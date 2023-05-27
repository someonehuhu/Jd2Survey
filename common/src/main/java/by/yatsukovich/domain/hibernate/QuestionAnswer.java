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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
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
        "response", "question"
})
@ToString()
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "question_answer")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_answer_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "response_id")
    @JsonBackReference
    @ToString.Exclude
    private Response response;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    @ToString.Exclude
    private Question question;

    @Type(type = "jsonb")
    @Column(name = "answer", columnDefinition = "jsonb")
    private Answer answer;

    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "changed_on")
    @UpdateTimestamp
    private Timestamp changed;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
