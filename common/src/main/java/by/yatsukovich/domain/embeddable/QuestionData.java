package by.yatsukovich.domain.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class QuestionData {

    private String text;

    private String resourcePath;
}
