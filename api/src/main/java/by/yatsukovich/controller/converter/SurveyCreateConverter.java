package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurveyCreateConverter implements Converter<SurveyCreateRequest, Survey> {

    @Override
    public Survey convert(SurveyCreateRequest source) {

        return Survey.builder()
                .name(source.getName())
                .accessCodeword(source.getAccessCodeword())
                .respondersLimit(source.getRespondersLimit())
                .validityDate(null)
                .timeLimit(source.getTimeLimit())
                .build();
    }
}
