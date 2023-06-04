package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    Long countByQuestionIdAndResponseIdIn(Long questionId, List<Long> responseIds);

    List<QuestionAnswer> findAllByQuestionIdAndResponseIdIn(Long questionId, List<Long> responseIds);

}
