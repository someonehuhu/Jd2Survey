package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query(value = "select count(r.response_id) from response r " +
            "where r.survey_id= :surveyId and r.response_status= :#{#status.name}", nativeQuery = true)
    long countBySurveyIdWithStatus(@Param("surveyId") Long surveyId, @Param("status") ResponseStatus status);

}
