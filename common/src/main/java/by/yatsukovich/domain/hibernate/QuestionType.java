package by.yatsukovich.domain.hibernate;

import by.yatsukovich.domain.enums.QuestionKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "questions"
})
@ToString(exclude = {
        "questions"
})
@Entity
@Table(name = "c_question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionKind questionKind;

    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    private Set<Question> questions;

}
