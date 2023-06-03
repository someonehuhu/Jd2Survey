package by.yatsukovich.controller.dto;

import by.yatsukovich.domain.enums.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Builder
public class ResponseDto {

    Long id;

    ResponseStatus responseStatus;

    Timestamp completionDate;

    Long spentTime;

    List<QuestionAnswerDto> questionAnswers;

}
