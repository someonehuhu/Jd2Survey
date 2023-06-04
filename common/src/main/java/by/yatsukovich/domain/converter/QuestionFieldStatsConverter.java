package by.yatsukovich.domain.converter;

import by.yatsukovich.domain.hibernate.QuestionField;
import by.yatsukovich.domain.hibernate.view.QuestionFieldStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface QuestionFieldStatsConverter {

    @Mapping(target = "fieldText", source = "questionData.text")
    QuestionFieldStats fromQuestionField(QuestionField questionField);
}
