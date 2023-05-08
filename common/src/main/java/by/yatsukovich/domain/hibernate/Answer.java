package by.yatsukovich.domain.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {
    private Long questionId;

    private Set<Long> chosenFields;

    private String answerText;

}
