package by.yatsukovich.domain.hibernate;

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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "question"
})
@ToString(exclude = {
        "question"
})
@Entity
@Table(name = "question_field")
public class QuestionField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Embedded
    @AttributeOverride(name = "text", column = @Column(name = "field_text"))
    @AttributeOverride(name = "resourcePath", column = @Column(name = "resource_path"))
    private QuestionData questionData;

    @Column(name = "is_right")
    private Boolean isRight;



}
