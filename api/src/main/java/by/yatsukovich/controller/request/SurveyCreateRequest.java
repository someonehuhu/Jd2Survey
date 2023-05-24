package by.yatsukovich.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;


@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class SurveyCreateRequest {

    private List<QuestionCreateRequest> questionCreateRequests;

    private List<MailingCreateRequest> mailingCreateRequests;

    @NotNull()
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 15)
    private String accessCodeword;

    private Integer respondersLimit;

    @Pattern(regexp = "[\\d]+0{3}")
    private String validityDate;

    private Long timeLimit;

}
