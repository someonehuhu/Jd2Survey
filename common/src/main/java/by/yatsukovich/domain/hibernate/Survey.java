package by.yatsukovich.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@EqualsAndHashCode(exclude = {
        "owner", "responses", "questions", "mailings"
})
@ToString()
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    @ToString.Exclude
    private User owner;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private List<Question> questions;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private List<Response> responses;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private List<Mailing> mailings;

    @Column(name = "survey_name")
    private String name;

    @Column(name = "access_codeword")
    private String accessCodeword;

    @Column(name = "responders_limit")
    private Integer respondersLimit;

    @Column(name = "validity_date")
    private Timestamp validityDate;

    @Column(name = "time_limit")
    private Long timeLimit;


    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "changed_on")
    @UpdateTimestamp
    private Timestamp changed;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
