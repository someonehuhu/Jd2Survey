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
        //get same for all entities current timestamp
        Timestamp currentTimestamp = timestampUtil.getCurrentTimestamp();
        //convert base survey fields
        Survey survey = conversionService.convert(surveyCreateRequest, Survey.class);
        //get mailings from survey create request
        List<Mailing> mailings = new ArrayList<>();
        for (MailingCreateRequest mailingCreateRequest : surveyCreateRequest.getMailingCreateRequests()) {
            Mailing mailing = conversionService.convert(mailingCreateRequest, Mailing.class);
            //initialize system fields
            mailing.setCreated(currentTimestamp);
            mailing.setChanged(currentTimestamp);
            mailing.setDeleted(false);
            //initialize mailing with parent survey
            mailing.setSurvey(survey);
            //collect mailings
            mailings.add(mailing);
        }
        //get questions from survey create request
        List<Question> questions = new ArrayList<>();
        for (QuestionCreateRequest questionCreateRequest : surveyCreateRequest.getQuestionCreateRequests()) {
            Question question = conversionService.convert(questionCreateRequest, Question.class);
            //initialize system fields
            question.setCreated(currentTimestamp);
            question.setChanged(currentTimestamp);
            question.setDeleted(false);
            //initialize question with parent survey
            question.setSurvey(survey);
            //get question fields from request
            List<QuestionField> questionFields = new ArrayList<>();
            for (QuestionFieldCreateRequest questionFieldCreateRequest
                    : questionCreateRequest.getQuestionFieldCreateRequests()) {
                QuestionField questionField = conversionService.convert(questionFieldCreateRequest, QuestionField.class);
                //initialize system fields
                questionField.setCreated(currentTimestamp);
                questionField.setChanged(currentTimestamp);
                questionField.setDeleted(false);
                //initialize field with parent question
                questionField.setQuestion(question);
                //collect fields
                questionFields.add(questionField);
            }
            question.setQuestionFields(questionFields);

            questions.add(question);
        }

        survey.setCreated(currentTimestamp);
        survey.setChanged(currentTimestamp);
        survey.setDeleted(false);

        //initialize survey with child entities
        survey.setMailings(mailings);
        survey.setQuestions(questions);


        return survey;
    }

}
