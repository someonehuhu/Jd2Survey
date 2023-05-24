package by.yatsukovich.controller.request;

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

    private boolean isRight;

    @NotNull
    @Size(max = 50)
    private String fieldText;
}
