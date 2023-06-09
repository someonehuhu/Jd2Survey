package by.yatsukovich.service.impl;

import by.yatsukovich.domain.hibernate.Answer;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.QuestionAnswer;
import by.yatsukovich.domain.hibernate.QuestionField;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.exception.ExceptionMessageGenerator;
import by.yatsukovich.exception.ValidationException;
import by.yatsukovich.repository.springdata.QuestionRepository;
import by.yatsukovich.service.AnswerService;
import by.yatsukovich.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;

    private final ExceptionMessageGenerator exceptionMessageGenerator;

    private final QuestionService questionService;

    @Deprecated
    @Override
    public QuestionAnswer validateAnswerAndMapToDomain(AnswerView answerView) {
        Optional<Question> questionOptional = questionRepository.findById(answerView.getQuestionId());
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Answer answer = answerView.getAnswer();
            switch (question.getQuestionType()) {
                case PLAIN -> validatePlain(question, answer);
                case CHECKBOX -> validateCheckbox(question, answer);
                case DROP_DOWN_LIST -> validateDropdownList(question, answer);
            }
            return QuestionAnswer.builder()
                    .answer(answer)
                    .question(question)
                    .build();
        } else {
            throw new ValidationException("Question with specified id not exists!");
        }
    }

    @Override
    public QuestionAnswer validateAnswerViewByQuestions(AnswerView answerView, List<Question> questions) {
        Optional<Question> questionOptional = questionService.findFromListById(answerView.getQuestionId(), questions);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Answer answer = answerView.getAnswer();
            switch (question.getQuestionType()) {
                case PLAIN -> validatePlain(question, answer);
                case CHECKBOX -> validateCheckbox(question, answer);
                case DROP_DOWN_LIST -> validateDropdownList(question, answer);
            }
            return QuestionAnswer.builder()
                    .answer(answer)
                    .question(question)
                    .build();
        } else {
            throw new ValidationException("Question with specified id not exists!");
        }
    }

    private void validatePlain(Question question, Answer answer) {
        validatePlainQuestionMandatory(question, answer);
    }

    private void validateCheckbox(Question question, Answer answer) {
        validateCheckBoxQuestionMandatory(question, answer);

        validateChosenFields(question, answer.getChosenFields());


    }

    private void validateDropdownList(Question question, Answer answer) {
        validateDropdownListQuestionMandatory(question, answer);
        if (answer.getChosenFields().size() != 1) {
            throw new ValidationException("Dropdown list validation exception.The only field must be chosen.");
        }
        validateChosenFields(question, answer.getChosenFields());
    }

    private void validateChosenFields(Question question, Iterable<Long> chosenFields) {
        List<QuestionField> questionFields = question.getQuestionFields();
        chosenFields.forEach(chosenField -> {
            Optional<QuestionField> foundOptional = questionFields.stream()
                    .filter(questionField -> questionField.getId().equals(chosenField))
                    .findAny();
            //
            if (foundOptional.isEmpty()) {
                throw new ValidationException("Chosen field does not exist in question with id =" + question.getId());
            }
        });
    }

    private void validateMandatory(Question sourceQuestion, Answer answer, Predicate<Answer> validatePredicate) {
        if (sourceQuestion.isMandatory()) {
            if (!validatePredicate.test(answer)) {
                String message = exceptionMessageGenerator.generateMandatoryValidationMessage(sourceQuestion.getId());
                throw new ValidationException(message);
            }
        }
    }

    private void validatePlainQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> answer.getAnswerText() != null && !answer.getAnswerText().isEmpty()
        );
    }

    private void validateCheckBoxQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> !answer.getChosenFields().isEmpty()
        );
    }

    private void validateDropdownListQuestionMandatory(Question sourceQuestion, Answer toValidate) {
        validateMandatory(
                sourceQuestion,
                toValidate,
                answer -> !answer.getChosenFields().isEmpty()
        );
    }
}
