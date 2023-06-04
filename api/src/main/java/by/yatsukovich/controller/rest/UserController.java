package by.yatsukovich.controller.rest;

import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Transactional()
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        /*List<User> users = userService.findAll();*/
        Optional<User> user = userService.findByEmailNotDeleted("124@mail.ru");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
