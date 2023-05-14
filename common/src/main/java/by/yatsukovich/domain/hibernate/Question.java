package by.yatsukovich.domain.hibernate;

import by.yatsukovich.domain.embeddable.QuestionData;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
        "survey", "questionType"
})
@ToString()
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    @ToString.Exclude
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_type_id")
    @ToString.Exclude
    private QuestionType questionType;

    @OneToMany(mappedBy = "question" , cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    private Set<QuestionAnswer> questionAnswers;

    @Embedded
    @AttributeOverride(name = "text", column = @Column(name = "question_text"))
    @AttributeOverride(name = "resourcePath", column = @Column(name = "resource_path"))
    private QuestionData questionData;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;

}
