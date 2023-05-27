package by.yatsukovich.controller.mapper;

import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.Survey;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public class SurveyContext {

    private Survey survey;

    @AfterMapping
    public void afterQuestionRequestMappingMapping(
            @MappingTarget Question question) {
        question.setSurvey(survey);
    }

}
