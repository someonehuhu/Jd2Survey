package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Answer;
import by.yatsukovich.domain.hibernate.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    void validatePlainQuestionMandatory(Question sourceQuestion, Answer answer);

    void validateCheckBoxQuestionMandatory(Question sourceQuestion, Answer answer);

    void validateDropdownListQuestionMandatory(Question sourceQuestion, Answer answer);

    Optional<Question> findFromListById(Long id, List<Question> questions);

}
