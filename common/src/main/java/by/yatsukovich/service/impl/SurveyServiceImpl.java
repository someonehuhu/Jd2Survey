package by.yatsukovich.service.impl;

import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.repository.springdata.QuestionFieldRepository;
import by.yatsukovich.repository.springdata.QuestionRepository;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    private final QuestionRepository questionRepository;

    private final QuestionFieldRepository questionFieldRepository;

    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }
}
