package by.yatsukovich.domain.hibernate.view;

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
public class SurveyStats {

    private Long surveyId;

    private List<QuestionStats> questionStats;
}
