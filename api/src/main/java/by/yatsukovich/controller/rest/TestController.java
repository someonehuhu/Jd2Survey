package by.yatsukovich.controller.rest;

import by.yatsukovich.domain.embeddable.QuestionData;
import by.yatsukovich.domain.hibernate.Question;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.repository.springdata.SurveyRepository;
import by.yatsukovich.security.util.PrincipalUtils;
import by.yatsukovich.service.UserService;
import by.yatsukovich.util.TimestampUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    //private final QuestionTypeRepository questionTypeRepository;

    private final SurveyRepository surveyRepository;

    private final UserService userService;

    private final PrincipalUtils principalUtils;

    private final TimestampUtil timestampUtil;

    @PostMapping
    public ResponseEntity<Object> putTestSurvey(Principal principal) {

        //String username = principalUtils.getUsername(principal);

        Optional<User> optionalUser = userService.findByEmailNotDeleted("admin@mail.ru");

        Timestamp current = timestampUtil.now();

        Survey survey = Survey.builder()
                .name("test_survey")
               /* .created(current)
                .changed(current)
                //.deleted(false)*/
                .build();
        survey.setOwner(optionalUser.get());

        List<Question> questions = new ArrayList<>();

        /*NOTE : In JPA @*To* relationships both parent and child entities must be cross assigned before (parent) saving.*/
        for (int i = 0; i < 3; i++) {
            //QuestionType questionType = questionTypeRepository.findOneByType(QuestionTypes.PLAIN).get();

            Question question = Question.builder()
                    .survey(survey)
                    .questionData(QuestionData.builder()
                            .text(i + " question")
                            .build())
                    .isMandatory(false)
                    //.type(QuestionTypes.PLAIN)
                    /*.deleted(false)
                    .changed(current)
                    .created(current)*/
                    .build();

            questions.add(question);
        }

        survey.setQuestions(questions);

        survey = surveyRepository.save(survey);

        return new ResponseEntity<>(survey, HttpStatus.CREATED);

    }
}
