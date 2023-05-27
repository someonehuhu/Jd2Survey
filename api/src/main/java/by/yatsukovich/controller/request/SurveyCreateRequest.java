package by.yatsukovich.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;


@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class SurveyCreateRequest {

    private List<QuestionCreateRequest> questions;

    private List<MailingCreateRequest> mailings;

    @NotNull()
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 15)
    private String accessCodeword;

    @Positive
    private Integer respondersLimit;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp validityDate;

    private Long timeLimit;

}
