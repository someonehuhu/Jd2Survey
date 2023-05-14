package by.yatsukovich.service.impl;

import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.repository.springdata.UserRepository;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByAuthenticationInfoEmail(email);
    }
}
