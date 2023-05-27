package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    @Deprecated
    public Optional<User> findOneByAuthenticationInfoEmail(String mail);

    public Optional<User> findOneByAuthenticationInfoEmailAndIsDeletedFalse(String mail);

}
