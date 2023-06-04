package by.yatsukovich.service.impl;

import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.exception.DraftDeniedCause;
import by.yatsukovich.exception.EntityNotFoundException;
import by.yatsukovich.exception.ExceptionMessageGenerator;
import by.yatsukovich.exception.ResponseDraftDeniedException;
import by.yatsukovich.repository.springdata.ResponseRepository;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.service.AnswerService;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.UserService;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final ExceptionMessageGenerator exceptionMessageGenerator;

    @Override
    public Response getDraftedUserResponse(User responder, Long responseId) {
        Optional<Response> optionalResponse = responseRepository.findById(responseId);
        if (optionalResponse.isPresent()) {
            Response response = optionalResponse.get();
            if (response.getResponder().getId().equals(responder.getId())//if responder ids matches
                    && response.getResponseStatus().equals(ResponseStatus.DRAFTED)) {//and if response is drafted

                return response;
            } else {
                throw new IllegalArgumentException("Illegal responder id = " + responder.getId());
            }
        } else {
            String message = exceptionMessageGenerator.generateEntityNotFoundMessage(Response.class.getName(), responseId);
            throw new EntityNotFoundException(message);
        }
    }

    @Override
    public Response draftResponse(String responderName, Long surveyId, String accessCodeword) {
        Survey survey = surveyRepository.findByIdAndIsDeletedFalse(surveyId);
        if (survey != null) {
            Optional<User> optionalUser = userService.findByEmailNotDeleted(responderName);

            if (optionalUser.isPresent()) {
                User responder = optionalUser.get();
                //check if already exists
                Optional<Response> optionalResponse = survey.getResponses().stream()
                        .filter(response -> response.getResponder().equals(responder))
                        .findFirst();
                if (optionalResponse.isPresent()) {
                    String message = exceptionMessageGenerator.generateDraftDeniedMessage(DraftDeniedCause.ALREADY_EXISTS);
                    throw new ResponseDraftDeniedException(message);
                }

                validateAccessCodeword(survey.getAccessCodeword(), accessCodeword);
                validateOnExpired(timestampUtil.now());
                validateRespondersLimit(surveyId, survey.getRespondersLimit());

                Response response = Response.builder()
                        .responder(responder)
                        .survey(survey)
                        .responseStatus(ResponseStatus.DRAFTED)
                        .build();

                return responseRepository.save(response);
            } else {
                String message = exceptionMessageGenerator.generateUserNotFoundMessage(responderName);
                throw new EntityNotFoundException(message);
            }
        }
        String message = exceptionMessageGenerator.generateEntityNotFoundMessage(Survey.class.getName(), surveyId);
        throw new EntityNotFoundException(message);

    }


    @Override
    @Deprecated
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
    public Response validateAndSaveAnswers(Response response, Long spentTime, List<AnswerView> answerViews) {
        response.setSpentTime(spentTime);

        if (!validateResponseDuration(response)) {
            response.setResponseStatus(ResponseStatus.TIME_EXPIRED);
        } else {
            List<Question> questions = response.getSurvey().getQuestions();
            //validate answers and map to domain class
            List<QuestionAnswer> questionAnswers = answerViews.stream()
                    .map(answerView -> {
                        QuestionAnswer mapped = answerValidationService.validateAnswerViewByQuestions(answerView, questions);
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
                String message = exceptionMessageGenerator.generateDraftDeniedMessage(DraftDeniedCause.ILLEGAL_CODEWORD);
                throw new ResponseDraftDeniedException(message);
            }
        }
    }

    private void validateOnExpired(Timestamp toValidate) {
        if (toValidate != null) {
            if (timestampUtil.now().before(toValidate)) {
                String message = exceptionMessageGenerator.generateDraftDeniedMessage(DraftDeniedCause.EXPIRED);
                throw new ResponseDraftDeniedException(message);
            }
        }
    }

    private void validateRespondersLimit(Long surveyId, Integer respondersLimit) {
        long currentCount = responseRepository.countBySurveyIdWithStatus(surveyId, ResponseStatus.SUCCESS);
        if (respondersLimit != null) {
            if (respondersLimit.compareTo((int) ++currentCount) < 0) {
                throw new ResponseDraftDeniedException("out of limit");
            }
        }
    }
}
