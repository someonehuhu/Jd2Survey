package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.UserCreateRequest;
import by.yatsukovich.domain.hibernate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<UserCreateRequest, User> {

    private final PasswordEncoder passwordEncoder;


    public User convert(UserCreateRequest source) {
        /*String encodedPassword = passwordEncoder.encode(source.getPassword());
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(source.getEmail(),  encodedPassword);
        List<UserRole> userRoles =*/

        return null;
    }
}
