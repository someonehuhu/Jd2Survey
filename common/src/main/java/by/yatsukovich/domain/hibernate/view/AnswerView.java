package by.yatsukovich.domain.hibernate.view;

import by.yatsukovich.domain.hibernate.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerView {
    private Long questionId;

    private Answer answer;
}
