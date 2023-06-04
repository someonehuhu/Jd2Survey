package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.hibernate.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Survey findByIdAndIsDeletedFalse(Long id);


}
