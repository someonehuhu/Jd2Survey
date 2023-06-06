package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.enums.SystemRoles;
import by.yatsukovich.domain.hibernate.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findBySystemRole(SystemRoles systemRole);


}
