package by.yatsukovich.domain.embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Deprecated
public class QuestionResponseKeys {
    private Long questionId;

    private Long responseId;
}
