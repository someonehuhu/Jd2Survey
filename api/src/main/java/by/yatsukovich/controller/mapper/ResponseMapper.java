package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.DraftResponseDto;
import by.yatsukovich.controller.dto.ResponseDto;
import by.yatsukovich.domain.hibernate.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                QuestionAnswerMapper.class,
                QuestionMapper.class
        }
)
public interface ResponseMapper {

    @Mapping(target = "responseId", source = "response.id")
    @Mapping(target = "surveyId", source = "response.survey.id")
    @Mapping(target = "name", source = "response.survey.name")
    @Mapping(target = "questions", source = "response.survey.questions")
    @Mapping(target = "timeLimit", source = "response.survey.timeLimit")
    DraftResponseDto responseToDraftResponseDto(Response response);


    ResponseDto responseToResponseDto(Response response);

    List<ResponseDto> responsesToResponseDtoList(List<Response> response);
}
