package by.yatsukovich.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Object with survey info on create stage")
public class SurveyCreateRequest {

    private List<QuestionCreateRequest> questions;

    private List<MailingCreateRequest> mailings;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "test survey", type = "string", description = "survey name")
    @NotNull()
    @Size(min = 1, max = 50)
    private String name;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "secret", type = "string", description = "codeword to access the survey on draft stage")
    @Size(max = 15)
    private String accessCodeword;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "100", type = "integer", description = "limit on the number of responders ")
    @Positive
    private Integer respondersLimit;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2021-31-21", type = "string", description = "survey end date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp validityDate;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "6000", type = "long", description = "response time limit in seconds")
    private Long timeLimit;

}
