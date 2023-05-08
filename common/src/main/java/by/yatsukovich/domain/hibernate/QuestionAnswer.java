package by.yatsukovich.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "response", "question"
})
@ToString(exclude = {
        "response", "question"
})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "question_answer")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /*@Embedded
    @AttributeOverride(name = "questionId", column = @Column(name = "question_id"))
    @AttributeOverride(name = "responseId", column = @Column(name = "response_id"))
    private QuestionResponseKeys questionResponseKeys;*/

    @ManyToOne
    @JoinColumn(name = "response_id")
    @JsonBackReference
    private Response response;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Type(type = "jsonb")
    @Column(name = "answer", columnDefinition = "jsonb")
    private Answer answer;


}
