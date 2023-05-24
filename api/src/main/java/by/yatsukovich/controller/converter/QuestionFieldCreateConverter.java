package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.QuestionFieldCreateRequest;
import by.yatsukovich.domain.embeddable.QuestionData;
import by.yatsukovich.domain.hibernate.QuestionField;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionFieldCreateConverter implements Converter<QuestionFieldCreateRequest, QuestionField> {

    @Override
    public QuestionField convert(QuestionFieldCreateRequest source) {
        return QuestionField.builder()
                .isRight(source.isRight())
                .questionData(QuestionData.builder()
                        .text(source.getFieldText())
                        .build())
                .build();
    }
}
