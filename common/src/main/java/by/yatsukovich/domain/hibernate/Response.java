package by.yatsukovich.domain.hibernate;

import by.yatsukovich.domain.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
        "survey", "responder"
})
@ToString(exclude = {
        "survey", "responder"
})
@Entity
@Table(name = "response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "responder_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private User responder;

    @Column(name = "response_status")
    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;

    @Column(name = "completion_date")
    private Timestamp completionDate;



}
