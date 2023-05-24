package by.yatsukovich.domain.hibernate;

import by.yatsukovich.domain.embeddable.QuestionData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "question"
})
@ToString()
@Entity
@Table(name = "question_field")
public class QuestionField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_field_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    @ToString.Exclude
    private Question question;

    @Embedded
    @AttributeOverride(name = "text", column = @Column(name = "field_text"))
    @AttributeOverride(name = "resourcePath", column = @Column(name = "resource_path"))
    private QuestionData questionData;

    @Column(name = "is_right")
    private Boolean isRight;

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;



}
