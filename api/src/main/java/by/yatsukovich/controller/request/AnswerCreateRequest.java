package by.yatsukovich.controller.request;

import by.yatsukovich.domain.hibernate.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class AnswerCreateRequest {

    @NotNull
    @Positive
    private Long questionId;

    @NotNull
    private Answer answer;


}
