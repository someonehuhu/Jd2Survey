package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.QuestionCreateRequest;
import by.yatsukovich.domain.embeddable.QuestionData;
import by.yatsukovich.domain.hibernate.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionCreateConverter implements Converter<QuestionCreateRequest, Question> {

    @Override
    public Question convert(QuestionCreateRequest source) {

        return Question.builder()
                .mandatory(source.isMandatory())
                .type(source.getQuestionType())
                .questionData(
                        QuestionData.builder()
                                .text(source.getQuestionText())
                                .build()
                )
                .build();
    }
}
