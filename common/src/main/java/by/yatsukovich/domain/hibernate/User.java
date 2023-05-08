package by.yatsukovich.domain.hibernate;


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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "surveys", "responses"
})
@ToString(exclude = {
        "surveys", "responses"
})
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //SonarLint advice to remove @AnnotationOverrides wrapper from annotation group
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email"))
    @AttributeOverride(name = "password", column = @Column(name = "password"))
    private AuthenticationInfo authenticationInfo;

    @OneToMany(mappedBy = "responder" , cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JsonManagedReference
    //@ToString.Exclude
    private Set<Response> responses = Collections.emptySet();

    @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @JsonManagedReference
    //@ToString.Exclude
    private Set<Survey> surveys = Collections.emptySet();
}
