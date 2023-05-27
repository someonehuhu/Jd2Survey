package by.yatsukovich.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class QuestionFieldDto {

    private Long id;

    private String fieldText;

    private Boolean right;

}
