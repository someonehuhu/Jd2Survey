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
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "questions"
})
@ToString()
@Entity
@Table(name = "c_question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id", nullable = false)
    private Long id;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionKind questionKind;

    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude
    private Set<Question> questions;

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;

}
