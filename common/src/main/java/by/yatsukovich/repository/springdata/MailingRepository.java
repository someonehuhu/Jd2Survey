package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
}
