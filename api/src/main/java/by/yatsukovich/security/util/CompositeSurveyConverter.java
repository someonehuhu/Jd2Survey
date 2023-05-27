package by.yatsukovich.security.util;

import by.yatsukovich.controller.request.MailingCreateRequest;
import by.yatsukovich.controller.request.QuestionCreateRequest;
import by.yatsukovich.controller.request.QuestionFieldCreateRequest;
import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Mailing;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionField;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Deprecated
public class CompositeSurveyConverter {

    private final ConversionService conversionService;

    private final TimestampUtil timestampUtil;

    /**
     * Method converts survey create request to Survey object, collecting all sub entities from their sub requests.
     * All entities will be initialized with their appropriate "parents", except the survey with it's owner!
     *
     * @param surveyCreateRequest which consists from child sub entities.
     * @return Survey.class object converted from request.
     */
    public Survey convertFromCreateRequest(SurveyCreateRequest surveyCreateRequest) {
       return null;
    }

}
