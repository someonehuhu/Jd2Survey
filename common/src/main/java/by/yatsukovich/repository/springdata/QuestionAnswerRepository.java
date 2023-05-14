package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
