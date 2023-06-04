package by.yatsukovich.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class QuestionFieldCreateRequest {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false", type = "boolean", description = "is field right if survey is test")
    private boolean isRight;

    @NotNull
    @Size(max = 50)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "not satisfied", type = "string", description = "question field text")
    private String fieldText;
}
