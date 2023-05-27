package by.yatsukovich.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Builder
public class SurveyDto {

    private Long id;

    private List<QuestionDto> questions;

    private List<MailingDto> mailings;

    private String name;

    private String accessCodeword;

    private Integer respondersLimit;

    private Timestamp validityDate;

    private Long timeLimit;

}
