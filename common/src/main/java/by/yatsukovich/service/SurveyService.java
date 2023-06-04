package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.view.SurveyStats;

import java.util.Optional;

public interface SurveyService {

    Survey save(Survey survey);

    Survey findById(Long id);

    SurveyStats getSurveyStats(Long surveyId, int page, int size);



}
