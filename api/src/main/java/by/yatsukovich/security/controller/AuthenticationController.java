package by.yatsukovich.security.controller;

import by.yatsukovich.security.dto.AuthRequest;
import by.yatsukovich.security.dto.AuthResponse;
import by.yatsukovich.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    private final UserDetailsService userDetailsProvider;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getLogin(),
                        authRequest.getPassword() /*+ configuration.getServerPasswordSalt()*/
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(authRequest.getLogin())
                        .token(
                                tokenUtils.generateToken(
                                        userDetailsProvider.loadUserByUsername(authRequest.getLogin())
                                )
                        )
                        .build()
        );
    }
}
