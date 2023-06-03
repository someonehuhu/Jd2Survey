package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Survey;

import java.util.Optional;

public interface SurveyService {

    public Survey save(Survey survey);

    public Survey findById(Long id);

    public Survey draftSurvey(Long responderId, Long surveyId, String accessCodeword);


}
