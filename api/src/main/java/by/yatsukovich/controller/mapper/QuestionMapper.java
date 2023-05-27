package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.QuestionDto;
import by.yatsukovich.controller.request.QuestionCreateRequest;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.Survey;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                QuestionFieldMapper.class,
        }
)
public interface QuestionMapper {

    @Mapping(target = "questionText", source = "questionData.text")
    QuestionDto questionToQuestionDto(Question question);

    default Question questionCreateRequestToQuestion(
            QuestionCreateRequest questionCreateRequest, @MappingTarget Question question) {
        return questionCreateRequestToQuestion(questionCreateRequest, question, question);
    }

    @Mapping(target = "questionFields", qualifiedByName = "FieldsWithQuestion")
    @Mapping(target = "questionData.text", source = "questionCreateRequest.questionText")
    Question questionCreateRequestToQuestion(
            QuestionCreateRequest questionCreateRequest, @MappingTarget Question question, @Context Question context);


    @Named("QuestionsWithSurvey")
    default List<Question> questionResourcesToQuestions(List<QuestionCreateRequest> sources, @Context Survey survey) {
        List<Question> questions;
        if (sources != null) {
            questions = sources.stream()
                    .map(questionCreateRequest -> {
                        Question question = new Question();
                        question = questionCreateRequestToQuestion(questionCreateRequest, question);
                        question.setSurvey(survey);
                        return question;
                    })
                    .toList();
        } else {
            questions = Collections.emptyList();
        }

        return questions;
    }

}
