package by.yatsukovich.service;

import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.view.AnswerView;

public interface AnswerService {

    QuestionAnswer validateAnswerAndMapToDomain(AnswerView answerView);


}
