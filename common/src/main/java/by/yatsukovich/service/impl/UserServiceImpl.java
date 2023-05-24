package by.yatsukovich.service.impl;

import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.UserRole;
import by.yatsukovich.repository.springdata.UserRepository;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByAuthenticationInfoEmail(email);
    }

    @Override
    public Optional<User> findByEmailNotDeleted(String email) {
        return userRepository.findOneByAuthenticationInfoEmailAndDeletedFalse(email);
    }

    @Override
    public Set<UserRole> getUserAuthorities(Long userId) {
        return null;
    }
}
