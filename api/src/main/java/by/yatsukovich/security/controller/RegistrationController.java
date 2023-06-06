package by.yatsukovich.security.controller;

import by.yatsukovich.controller.request.UserCreateRequest;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.repository.springdata.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserRepository userRepository;

    private final ConversionService conversionService;


    @PostMapping
    public ResponseEntity<Map<String, Object>> registration(@Valid @RequestBody UserCreateRequest userCreateRequest) {


        User user = conversionService.convert(userCreateRequest, User.class);

        user = userRepository.save(user);

        return new ResponseEntity<>(Map.of("user", user), HttpStatus.CREATED);

    }
}
