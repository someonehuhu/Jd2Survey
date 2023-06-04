package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
