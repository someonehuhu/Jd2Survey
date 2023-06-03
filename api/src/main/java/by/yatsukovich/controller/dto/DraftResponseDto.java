package by.yatsukovich.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class DraftResponseDto {

    private Long responseId;

    private Long surveyId;

    private String name;

    private Long timeLimit;

    private List<QuestionDto> questions;

}
