package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.view.AnswerView;

import java.util.List;

public interface AnswerService {

    QuestionAnswer validateAnswerAndMapToDomain(AnswerView answerView);

    QuestionAnswer validateAnswerViewByQuestions(AnswerView answerView, List<Question> questions);


}
