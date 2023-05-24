package by.yatsukovich.domain.hibernate;

import by.yatsukovich.domain.enums.SystemRoles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "users"
})
@ToString()
@Entity
@Table(name = "c_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private SystemRoles systemRole;

    @ManyToMany(mappedBy = "userRoles", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("userRoles")
    @ToString.Exclude
    private List<User> users = Collections.emptyList();

}
