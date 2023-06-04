package by.yatsukovich.service.impl;

import by.yatsukovich.domain.hibernate.Answer;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.exception.ExceptionMessageGenerator;
import by.yatsukovich.exception.ValidationException;
import by.yatsukovich.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final ExceptionMessageGenerator exceptionMessageGenerator;

    @Override
    public void validatePlainQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> answer.getAnswerText() != null && !answer.getAnswerText().isEmpty()
        );
    }

    @Override
    public void validateCheckBoxQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> !answer.getChosenFields().isEmpty()
        );
    }

    @Override
    public void validateDropdownListQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> !answer.getChosenFields().isEmpty()
        );
    }

    @Override
    public Optional<Question> findFromListById(Long id, List<Question> questions) {
        return questions.stream().
                filter(question -> question.getId().equals(id))
                .findFirst();
    }


    private void validateMandatory(Question sourceQuestion, Answer answer, Predicate<Answer> validatePredicate) {
        if (sourceQuestion.isMandatory()) {
            if (!validatePredicate.test(answer)) {
                String message = exceptionMessageGenerator.generateMandatoryValidationMessage(sourceQuestion.getId());
                throw new ValidationException(message);
            }
        }
    }
}
