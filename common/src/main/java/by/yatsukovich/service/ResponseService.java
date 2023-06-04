package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.domain.hibernate.view.SurveyStats;

import java.util.List;

public interface ResponseService {

    Response getDraftedUserResponse(User responder, Long responseId);

    Response draftResponse(String responderName, Long surveyId, String accessCodeword);

    /*@Deprecated
    Response saveResponseAnswers(User responder, Long responseId, Long spentTime, List<AnswerView> answerViews);*/

    Response saveResponseAnswers(Response response, Long spentTime, List<AnswerView> answerViews);

    Response validateAndSaveAnswers(Response response, Long spentTime, List<AnswerView> answerViews);

    Response getSurveyResponse(Long responseId, Long surveyId);


}
