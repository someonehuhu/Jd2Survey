package by.yatsukovich.domain.hibernate.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFieldStats {

    private Long id;

    private Long answersNumber;

    private String fieldText;
}
