package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
