package by.yatsukovich.controller.rest;

import by.yatsukovich.controller.dto.SurveyDto;
import by.yatsukovich.controller.mapper.ResponseMapper;
import by.yatsukovich.controller.mapper.SurveyMapper;
import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.security.util.PrincipalUtils;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final ConversionService conversionService;

    private final SurveyService surveyService;

    private final ResponseService responseService;

    private final UserService userService;

    private final PrincipalUtils principalUtils;

    private final SurveyMapper surveyMapper;

    private final ResponseMapper responseMapper;

    @GetMapping("/{surveyId}")
    ResponseEntity<Map<String, Object>> getSurvey(@PathVariable Long surveyId) {

        if (surveyId != null && surveyId > 0) {
            Survey survey = surveyService.findById(surveyId);
            SurveyDto surveyDto = surveyMapper.surveyToSurveyDTO(survey);
            return new ResponseEntity<>(Map.of("survey", surveyDto), HttpStatus.OK);


        } else {
            return new ResponseEntity<>(Map.of("error", "Illegal id"), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    ResponseEntity<Map<String, Object>> saveSurvey(
            Principal principal,
            @Valid @RequestBody SurveyCreateRequest surveyCreateRequest,
            BindingResult result) {
        Map<String, Object> resultMap;

        if (result.hasErrors()) {
            resultMap = result.getModel();
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        //get user by principles
        Optional<User> optionalUser = principalUtils.getUserOptional(principal);

        if (optionalUser.isPresent()) {
            Survey survey = surveyMapper.surveyCreateRequestToSurvey(surveyCreateRequest, new Survey());
            survey.setOwner(optionalUser.get());
            survey = surveyService.save(survey);

            SurveyDto surveyDto = surveyMapper.surveyToSurveyDTO(survey);

            resultMap = Map.of("survey", surveyDto);

            return new ResponseEntity<>(resultMap, HttpStatus.CREATED);
        } else {
            //unreachable statement?
            resultMap = Map.of("Error", "User not found by principles !");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }

    }


}
