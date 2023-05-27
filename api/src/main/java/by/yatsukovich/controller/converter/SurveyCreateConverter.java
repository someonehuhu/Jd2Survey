package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.mapper.SurveyContext;
import by.yatsukovich.controller.mapper.SurveyMapper;
import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurveyCreateConverter implements Converter<SurveyCreateRequest, Survey> {

    private final SurveyMapper surveyMapper;

    @Override
    public Survey convert(SurveyCreateRequest source) {

        return surveyMapper.surveyCreateRequestToSurvey(source, new Survey());

    }
}
