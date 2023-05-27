package by.yatsukovich.controller.request;

import by.yatsukovich.domain.enums.QuestionTypes;
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
public class QuestionCreateRequest {

    @NotNull
    private QuestionTypes questionType;

    @NotNull
    @Size(max = 200)
    private String questionText;

    @NotNull
    private Boolean mandatory;

    private List<QuestionFieldCreateRequest> questionFields;
}
