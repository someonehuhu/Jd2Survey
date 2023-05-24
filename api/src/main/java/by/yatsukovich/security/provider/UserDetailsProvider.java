package by.yatsukovich.security.provider;

import by.yatsukovich.domain.embeddable.AuthenticationInfo;
import by.yatsukovich.domain.enums.SystemRoles;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.UserRole;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> optionalUser = userService.findByEmailNotDeleted(username);
            //
            if (optionalUser.isPresent()) {
                //
                User user = optionalUser.get();

                //System.out.println("Loaded user by user provider: " + user + "\nwith roles : " );
                user.getUserRoles().forEach(System.out::println);
                List<UserRole> roles = user.getUserRoles();
                AuthenticationInfo authenticationInfo = user.getAuthenticationInfo();
                //
                return new org.springframework.security.core.userdetails.User(
                        authenticationInfo.getEmail(),
                        authenticationInfo.getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(
                                roles
                                        .stream()
                                        .map(UserRole::getSystemRole)
                                        .map(SystemRoles::name)
                                        .collect(Collectors.joining(","))
                        )
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}
