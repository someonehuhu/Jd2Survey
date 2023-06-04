package by.yatsukovich.security.controller;

import by.yatsukovich.controller.request.UserCreateRequest;
import by.yatsukovich.repository.springdata.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registration(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        /*userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        User user = userMapper.userCreateRequestToUser(userCreateRequest);

        user = userRepository.save(user);

        return new ResponseEntity<>(Map.of("user", user), HttpStatus.CREATED);*/

        return null;
    }
}
