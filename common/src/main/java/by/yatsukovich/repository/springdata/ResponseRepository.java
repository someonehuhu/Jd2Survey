package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
