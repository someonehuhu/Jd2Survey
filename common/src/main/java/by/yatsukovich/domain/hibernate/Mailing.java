package by.yatsukovich.domain.hibernate;

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
@EqualsAndHashCode(exclude = "survey")
@ToString()
@Entity
@Table(name = "mailing")
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailing_id", nullable = false)
    private Long id;

    @Column(name = "mail")
    private String mailAddress;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    @ToString.Exclude
    @JsonBackReference
    private Survey survey;

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;



}
