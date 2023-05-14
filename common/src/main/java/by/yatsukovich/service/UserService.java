package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
