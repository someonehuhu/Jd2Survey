package by.yatsukovich.domain.hibernate.view;

import by.yatsukovich.domain.enums.QuestionTypes;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStats {

    private Long id;

    private String questionText;

    private QuestionTypes questionType;

    private Long answerNumber;

    private List<QuestionFieldStats> questionFieldStats;

    private List<String> textAnswers;



}
