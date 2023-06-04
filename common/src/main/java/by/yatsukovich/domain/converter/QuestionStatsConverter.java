package by.yatsukovich.domain.converter;

import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.view.QuestionFieldStats;
import by.yatsukovich.domain.hibernate.view.QuestionStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                QuestionFieldStats.class
        }
)
public interface QuestionStatsConverter {

    @Mapping(target = "questionText", source = "questionData.text")
    @Mapping(target = "questionFieldStats", source = "questionFields")
    QuestionStats fromQuestion(Question question);

}
