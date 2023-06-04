package by.yatsukovich.controller.request;

import by.yatsukovich.domain.enums.QuestionTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
@Schema(description = "Object with question info on create stage")
public class QuestionCreateRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PLAIN", type = "QuestionTypes", description = "question type")
    @NotNull
    private QuestionTypes questionType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "are u ok?", type = "string", description = "question text")
    @NotNull
    @Size(max = 200)
    private String questionText;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean", description = "is question mandatory to answer")
    @NotNull
    private Boolean mandatory;

    private List<QuestionFieldCreateRequest> questionFields;
}
