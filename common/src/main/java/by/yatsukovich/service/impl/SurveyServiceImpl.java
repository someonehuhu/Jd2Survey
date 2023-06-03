package by.yatsukovich.service.impl;

import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.exception.ResponseDraftValidationException;
import by.yatsukovich.repository.springdata.QuestionFieldRepository;
import by.yatsukovich.repository.springdata.QuestionRepository;
import by.yatsukovich.repository.springdata.ResponseRepository;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final QuestionRepository questionRepository;

    private final QuestionFieldRepository questionFieldRepository;

    private final ResponseRepository responseRepository;

    private final TimestampUtil timestampUtil;

    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public Survey findById(Long id) {
        return surveyRepository.findByIdAndIsDeletedFalse(id);
    }

    @Transactional
    @Override
    public Survey draftSurvey(Long responderId, Long surveyId, String accessCodeword) {
        Survey survey = surveyRepository.findByIdAndIsDeletedFalse(surveyId);

        validateAccessCodeword(survey.getAccessCodeword(), accessCodeword);
        validateOnExpired(timestampUtil.now());
        validateRespondersLimit(surveyId, survey.getRespondersLimit());

        Response response = Response.builder()
                .survey(survey)
                .responseStatus(ResponseStatus.DRAFTED)
                .build();

        response = responseRepository.save(response);



        //TODO insert with native sql without getting all responses...

        return null;
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
