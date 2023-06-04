package by.yatsukovich.service.impl;

import by.yatsukovich.domain.converter.QuestionFieldStatsConverter;
import by.yatsukovich.domain.converter.QuestionStatsConverter;
import by.yatsukovich.domain.enums.QuestionTypes;
import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.QuestionField;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.view.QuestionFieldStats;
import by.yatsukovich.domain.hibernate.view.QuestionStats;
import by.yatsukovich.domain.hibernate.view.SurveyStats;
import by.yatsukovich.exception.ResponseDraftValidationException;
import by.yatsukovich.repository.springdata.QuestionAnswerRepository;
import by.yatsukovich.repository.springdata.ResponseRepository;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final ResponseRepository responseRepository;

    private final TimestampUtil timestampUtil;

    private final QuestionStatsConverter questionStatsConverter;

    private final QuestionFieldStatsConverter questionFieldStatsConverter;

    private final QuestionAnswerRepository questionAnswerRepository;

    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public Survey findById(Long id) {
        return surveyRepository.findByIdAndIsDeletedFalse(id);
    }

    @Override
    public SurveyStats getSurveyStats(Long surveyId, int page, int size) {
        Survey survey = surveyRepository.findByIdAndIsDeletedFalse(surveyId);

        List<Long> responsesIdList = responseRepository.findAllIdsBySurveyIdAndResponseStatus
                (surveyId, ResponseStatus.SUCCESS, page, size);

        List<QuestionStats> questionStatsList = survey.getQuestions().stream()
                .map(question -> getStatsFromQuestion(question, responsesIdList))
                .toList();

        SurveyStats surveyStats = SurveyStats.builder()
                .surveyId(surveyId)
                .build();

        surveyStats.setQuestionStats(questionStatsList);

        return surveyStats;
    }

    private QuestionStats getStatsFromQuestion(Question question, List<Long> responsesIds) {
        QuestionStats questionStats = questionStatsConverter.fromQuestion(question);
        List<QuestionAnswer> questionAnswers = questionAnswerRepository
                .findAllByQuestionIdAndResponseIdIn(question.getId(), responsesIds);

        Long answersCount = questionAnswerRepository.countByQuestionIdAndResponseIdIn(question.getId(), responsesIds);
        questionStats.setAnswerNumber(answersCount);

        if (isSelectiveQuestionType(question.getQuestionType())) {//count field answers count
            List<QuestionFieldStats> questionFieldStatsList = question.getQuestionFields().stream()
                    .map(questionField -> getStatsFromQuestionField(questionField, questionAnswers))
                    .toList();

            questionStats.setQuestionFieldStats(questionFieldStatsList);
        } else {//get not empty text answers
            List<String> plainQuestionAnswers = questionAnswers.stream()
                    .filter(questionAnswer -> questionAnswer.getAnswer().getAnswerText() != null
                            && !questionAnswer.getAnswer().getAnswerText().isEmpty())
                    .map(questionAnswer -> questionAnswer.getAnswer().getAnswerText())
                    .toList();

            questionStats.setTextAnswers(plainQuestionAnswers);
        }

        return questionStats;
    }


    private QuestionFieldStats getStatsFromQuestionField(QuestionField questionField, List<QuestionAnswer> questionAnswers) {
        QuestionFieldStats questionFieldStats = questionFieldStatsConverter.fromQuestionField(questionField);

        long answersNumber = questionAnswers.stream()
                //.filter(questionAnswer -> questionTypesMatches(questionAnswer, question))
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

    private boolean isSelectiveQuestionType(QuestionTypes questionTypes) {
        return questionTypes.equals(QuestionTypes.CHECKBOX)
                || questionTypes.equals(QuestionTypes.DROP_DOWN_LIST);
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
        long currentCount = responseRepository.countBySurveyIdWithStatus(surveyId, ResponseStatus.TIME_EXPIRED);
        if (respondersLimit != null) {
            if (respondersLimit.compareTo((int) ++currentCount) < 0) {
                throw new ResponseDraftValidationException("out of limit");
            }
        }
    }


}
