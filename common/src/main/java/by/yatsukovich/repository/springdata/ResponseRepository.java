package by.yatsukovich.repository.springdata;

import by.yatsukovich.domain.enums.ResponseStatus;
import by.yatsukovich.domain.hibernate.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query(value = "select count(r.response_id) from response r " +
            "where r.survey_id= :surveyId and r.response_status= :#{#status.name}", nativeQuery = true)
    long countBySurveyIdWithStatus(@Param("surveyId") Long surveyId, @Param("status") ResponseStatus status);

    Response findByIdAndSurveyId(Long responseId, Long surveyId);

    @Query(value = "select r.response_id from response r " +
            "where r.survey_id= :surveyId and r.response_status= :#{#status.name} " +
            "limit :size " +
            "offset :page", nativeQuery = true)
    List<Long> findAllIdsBySurveyIdAndResponseStatus(Long surveyId, ResponseStatus status, int page, int size);

}
