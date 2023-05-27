package by.yatsukovich.controller.dto;

import by.yatsukovich.domain.enums.QuestionTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class QuestionDto {

    private Long id;

    private String questionText;

    private QuestionTypes questionType;

    private List<QuestionFieldDto> questionFields;

    private boolean mandatory;


}
