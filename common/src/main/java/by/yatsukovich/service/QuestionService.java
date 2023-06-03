package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Answer;
import by.yatsukovich.domain.hibernate.Question;

public interface QuestionService {

    void validatePlainQuestionMandatory(Question sourceQuestion, Answer answer);

    void validateCheckBoxQuestionMandatory(Question sourceQuestion, Answer answer);

    void validateDropdownListQuestionMandatory(Question sourceQuestion, Answer answer);

}
