package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Survey findByIdAndIsDeletedFalse(Long id);

    //Survey findSurveyOnDraft(Long surveyId, ResponseStatus responseStatus);


}
