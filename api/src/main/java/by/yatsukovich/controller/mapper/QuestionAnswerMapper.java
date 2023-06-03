package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.QuestionAnswerDto;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                QuestionMapper.class,
        }
)
public interface QuestionAnswerMapper {

    @Mapping(target = "questionDto", source = "question")
    QuestionAnswerDto questionAnswerToDto(QuestionAnswer questionAnswer);
}
