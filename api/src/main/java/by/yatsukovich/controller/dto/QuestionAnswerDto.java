package by.yatsukovich.controller.dto;

import by.yatsukovich.domain.hibernate.Answer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class QuestionAnswerDto {

    private QuestionDto questionDto;

    private Answer answer;
}
