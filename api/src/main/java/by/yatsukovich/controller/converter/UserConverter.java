package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.UserCreateRequest;
import by.yatsukovich.domain.embeddable.AuthenticationInfo;
import by.yatsukovich.domain.enums.SystemRoles;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.UserRole;
import by.yatsukovich.repository.springdata.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<UserCreateRequest, User> {

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    public User convert(UserCreateRequest source) {
        String encodedPassword = passwordEncoder.encode(source.getPassword());
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(source.getEmail(),  encodedPassword);
        List<UserRole> userRoles =List.of(userRoleRepository.findBySystemRole(SystemRoles.ROLE_USER));

        return User.builder()
                .name(source.getName())
                .authenticationInfo(authenticationInfo)
                .userRoles(userRoles).build();
    }
}
