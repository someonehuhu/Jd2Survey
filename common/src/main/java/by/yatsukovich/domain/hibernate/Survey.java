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
import java.util.Set;

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
    private Set<Question> questions;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private Set<Response> responses;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude
    private Set<Mailing> mailings;

    @Column(name = "share_link")
    private String shareLink;

    @Column(name = "access_codeword")
    private String accessCodeword;

    @Column(name = "responders_limit")
    private Integer respondersLimit;

    @Column(name = "validity_date")
    private Timestamp validityDate;

    @Column(name = "time_limit")
    private long timeLimit;

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
