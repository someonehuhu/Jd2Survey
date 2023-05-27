package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.QuestionFieldDto;
import by.yatsukovich.controller.request.QuestionFieldCreateRequest;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionField;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface QuestionFieldMapper {

    @Mapping(target = "fieldText", source = "questionData.text")
    QuestionFieldDto questionToQuestionDto(QuestionField questionField);

    @Mapping(target = "questionData.text", source = "questionFieldCreateRequest.fieldText")
    QuestionField questionFieldCreateRequestToQuestionField(QuestionFieldCreateRequest questionFieldCreateRequest);

    @Named("FieldsWithQuestion")
    default List<QuestionField> questionResourcesToQuestions
            (List<QuestionFieldCreateRequest> sources, @Context Question question) {
        List<QuestionField> questionFields;
        if (sources != null) {
            questionFields = sources.stream()
                    .map(questionCreateRequest -> {
                        QuestionField questionField = questionFieldCreateRequestToQuestionField(questionCreateRequest);
                        questionField.setQuestion(question);
                        return questionField;
                    })
                    .toList();
        } else {
            questionFields = Collections.emptyList();
        }

        return questionFields;
    }

}
