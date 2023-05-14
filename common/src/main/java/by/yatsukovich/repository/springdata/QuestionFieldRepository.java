package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.QuestionField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionFieldRepository extends JpaRepository<QuestionField, Long> {
}
