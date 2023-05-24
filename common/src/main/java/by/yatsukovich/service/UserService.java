package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.UserRole;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailNotDeleted(String email);

    Set<UserRole> getUserAuthorities(Long userId);
}
