package by.yatsukovich.security.util;

import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalUtils {

    private final UserService userService;

    public String getUsername(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getUsername();
    }

    public String getPassword(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getPassword();
    }

    public Collection<GrantedAuthority> getAuthorities(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getAuthorities();
    }

    public Optional<by.yatsukovich.domain.hibernate.User> getUserOptional(Principal principal) {
        String username = getUsername(principal);
        return userService.findByEmailNotDeleted(username);
    }
}
