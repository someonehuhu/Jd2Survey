package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findOneByAuthenticationInfoEmail(String mail);
}
