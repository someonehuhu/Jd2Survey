package by.yatsukovich.service.impl;

import by.yatsukovich.domain.converter.QuestionFieldStatsConverter;
import by.yatsukovich.domain.converter.QuestionStatsConverter;
import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.QuestionField;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.domain.hibernate.view.QuestionFieldStats;
import by.yatsukovich.domain.hibernate.view.QuestionStats;
import by.yatsukovich.domain.hibernate.view.SurveyStats;
import by.yatsukovich.exception.ResponseDraftValidationException;
import by.yatsukovich.repository.springdata.QuestionAnswerRepository;
import by.yatsukovich.repository.springdata.ResponseRepository;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.service.AnswerService;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.UserService;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;

    private final SurveyRepository surveyRepository;

    private final TimestampUtil timestampUtil;

    private final UserService userService;

    private final AnswerService answerValidationService;

    private final QuestionStatsConverter questionStatsConverter;

    private final QuestionFieldStatsConverter questionFieldStatsConverter;

    private final QuestionAnswerRepository questionAnswerRepository;

    @Override
    public Response getDraftedUserResponse(User responder, Long responseId) {
        Optional<Response> optionalResponse = responseRepository.findById(responseId);
        if (optionalResponse.isPresent()) {
            Response response = optionalResponse.get();
            if (response.getResponder().getId().equals(responder.getId())//if responder ids matches
                    && response.getResponseStatus().equals(ResponseStatus.DRAFTED)) {//and if response is drafted

                return response;
            } else {
                throw new RuntimeException("Illegal responder id!");
            }
        } else {
            throw new RuntimeException("Response not found!");
        }
    }

    @Transactional
    @Override
    public Response draftResponse(String responderName, Long surveyId, String accessCodeword) {
        Survey survey = surveyRepository.findByIdAndIsDeletedFalse(surveyId);
        if (survey != null) {
            Optional<User> responder = userService.findByEmailNotDeleted(responderName);

            if (responder.isPresent()) {
                validateAccessCodeword(survey.getAccessCodeword(), accessCodeword);
                validateOnExpired(timestampUtil.now());
                validateRespondersLimit(surveyId, survey.getRespondersLimit());

                Response response = Response.builder()
                        .responder(responder.get())
                        .survey(survey)
                        .responseStatus(ResponseStatus.DRAFTED)
                        .build();

                return responseRepository.save(response);
            } else {
                throw new RuntimeException("Responder not found!");
            }
        }
        throw new RuntimeException("Survey not found!");

    }


    @Override
    public Response saveResponseAnswers(Response response, Long spentTime, List<AnswerView> answerViews) {
        response.setSpentTime(spentTime);

        if (!validateResponseDuration(response)) {
            response.setResponseStatus(ResponseStatus.TIME_EXPIRED);
        } else {
            //validate answers and map to domain class
            List<QuestionAnswer> questionAnswers = answerViews.stream()
                    .map(answerView -> {
                        QuestionAnswer mapped = answerValidationService.validateAnswerAndMapToDomain(answerView);
                        mapped.setResponse(response);
                        return mapped;
                    })
                    .toList();

            response.getQuestionAnswers().addAll(questionAnswers);
            response.setCompletionDate(timestampUtil.now());
            response.setResponseStatus(ResponseStatus.SUCCESS);

        }

        return responseRepository.save(response);
    }

    @Override
    public Response getSurveyResponse(Long responseId, Long surveyId) {
        return responseRepository.findByIdAndSurveyId(responseId, surveyId);
    }

    private QuestionStats getStatsFromPlainQuestion(Question question) {

        return getStatsFromQuestion(question);
    }

    private QuestionStats getStatsFromSelectiveQuestion(Question question) {
        QuestionStats questionStats = getStatsFromQuestion(question);

        List<QuestionFieldStats> questionFieldStatsList = question.getQuestionFields().stream()
                .map(this::getStatsFromQuestionField)
                .toList();

        questionStats.setQuestionFieldStats(questionFieldStatsList);

        return questionStats;
    }

    private QuestionStats getStatsFromQuestion(Question question) {
        QuestionStats questionStats = questionStatsConverter.fromQuestion(question);
        questionStats.setAnswerNumber((long) question.getQuestionAnswers().size());

        return questionStats;
    }

    private QuestionFieldStats getStatsFromQuestionField(QuestionField questionField) {
        Question question = questionField.getQuestion();
        QuestionFieldStats questionFieldStats = questionFieldStatsConverter.fromQuestionField(questionField);

        long answersNumber = question.getQuestionAnswers().stream()
                .filter(questionAnswer -> questionTypesMatches(questionAnswer, question))
                .filter(questionAnswer -> questionFieldIdsMatches(questionAnswer, questionField))
                .count();
        questionFieldStats.setAnswersNumber(answersNumber);

        return questionFieldStats;
    }

    private boolean questionTypesMatches(QuestionAnswer questionAnswer, Question question) {
        return questionAnswer.getQuestion().getQuestionType()
                .equals(question.getQuestionType());
    }

    private boolean questionFieldIdsMatches(QuestionAnswer questionAnswer, QuestionField questionField) {
        return questionAnswer.getAnswer().getChosenFields().contains(questionField.getId());
    }



    private boolean validateResponseDuration(Response response) {
        Long timeLimit = response.getSurvey().getTimeLimit();
        if (timeLimit != null) {
            return timeLimit.compareTo(response.getSpentTime()) >= 0;
        }

        return true;
    }


    private void validateAccessCodeword(String surveyCodeWord, String toValidate) {
        if (surveyCodeWord != null) {
            if (!surveyCodeWord.equals(toValidate)) {
                throw new ResponseDraftValidationException("illegal codeword");
            }
        }
    }

    private void validateOnExpired(Timestamp toValidate) {
        if (toValidate != null) {
            if (timestampUtil.now().before(toValidate)) {
                throw new ResponseDraftValidationException("expired");
            }
        }
    }

    private void validateRespondersLimit(Long surveyId, Integer respondersLimit) {
        long currentCount = responseRepository.countBySurveyIdWithStatus(surveyId, ResponseStatus.SUCCESS);
        if (respondersLimit != null) {
            if (respondersLimit.compareTo((int) ++currentCount) < 0) {
                throw new ResponseDraftValidationException("out of limit");
            }
        }
    }
}
