package by.yatsukovich.domain.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString()
public class AuthenticationInfo {

    private String email;

    @JsonIgnore
    private String password;
}
