package by.yatsukovich.domain.hibernate;


import by.yatsukovich.domain.embeddable.AuthenticationInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "surveys", "responses", "userRoles"
})
@ToString()
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties("users")
    @Fetch(value = FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<UserRole> userRoles;

    //SonarLint advice to remove @AnnotationOverrides wrapper from annotation group
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email"))
    @AttributeOverride(name = "password", column = @Column(name = "password"))
    private AuthenticationInfo authenticationInfo;

    @OneToMany(mappedBy = "responder" , cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private List<Response> responses = Collections.emptyList();

    @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @JsonManagedReference
    @ToString.Exclude
    private List<Survey> surveys = Collections.emptyList();

    @Column(name = "created_on")
    private Timestamp created;

    @Column(name = "changed_on")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
