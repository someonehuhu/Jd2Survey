package by.yatsukovich.controller.rest;

import by.yatsukovich.controller.dto.ResponseDto;
import by.yatsukovich.controller.dto.SurveyDto;
import by.yatsukovich.controller.mapper.ResponseMapper;
import by.yatsukovich.controller.mapper.SurveyMapper;
import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.Survey;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.SurveyStats;
import by.yatsukovich.exception.EntityNotFoundException;
import by.yatsukovich.exception.ExceptionMessageGenerator;
import by.yatsukovich.security.util.PrincipalUtils;
import by.yatsukovich.security.util.ValidationUtils;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    private final ResponseService responseService;

    private final PrincipalUtils principalUtils;

    private final SurveyMapper surveyMapper;

    private final ResponseMapper responseMapper;

    private final ValidationUtils validationUtils;

    private final ExceptionMessageGenerator exceptionMessageGenerator;

    @Transactional()
    @GetMapping("/{surveyId}")
    ResponseEntity<Map<String, Object>> getSurvey(Principal principal, @PathVariable Long surveyId) {

        if (validationUtils.positive(surveyId)) {
            Survey survey = surveyService.findById(surveyId);
            Optional<User> optionalUser = principalUtils.getUserOptional(principal);
            if (optionalUser.isPresent()) {
                SurveyDto surveyDto = surveyMapper.surveyToSurveyDTO(survey);
                if (survey.getOwner().equals(optionalUser.get())) {
                    return new ResponseEntity<>(Map.of("survey", surveyDto), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(Map.of("error", "Illegal user id"), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Map.of("error", "Illegal id"), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional()
    @GetMapping("/{surveyId}/responses/{responseId}")
    ResponseEntity<Map<String, Object>> getSurveyResponse(
            @PathVariable Long surveyId,
            @PathVariable Long responseId,
            Principal principal
    ) {
        if (validationUtils.positive(surveyId) && validationUtils.positive(responseId)) {
            Optional<User> optionalOwner = principalUtils.getUserOptional(principal);
            if (optionalOwner.isPresent()) {
                Response response = responseService.getSurveyResponse(responseId, surveyId);
                if (response.getSurvey().getOwner().equals(optionalOwner.get())) {
                    ResponseDto responseDto = responseMapper.responseToResponseDto(response);
                    return new ResponseEntity<>(Map.of("response", responseDto), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(Map.of("Error", "Forbidden"), HttpStatus.FORBIDDEN);
                }
            } else {
                String username = principalUtils.getUsername(principal);
                String message = exceptionMessageGenerator.generateUserNotFoundMessage(username);

                throw new EntityNotFoundException(message);
            }
        } else {
            return new ResponseEntity<>(Map.of("Error", "Illegal path variables!"), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional()
    @GetMapping("/{surveyId}/responses")
    ResponseEntity<Map<String, Object>> getSurveyStats(
            Principal principal,
            @PathVariable Long surveyId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        if (validationUtils.positive(surveyId)
                && validationUtils.nonNull(page)
                && validationUtils.positive(size)) {
            Optional<User> optionalOwner = principalUtils.getUserOptional(principal);

            if (optionalOwner.isPresent()) {
                SurveyStats surveyStats = surveyService.getSurveyStats(surveyId, page, size);

                return new ResponseEntity<>(Map.of("surveyStats", surveyStats), HttpStatus.OK);
            } else {
                String username = principalUtils.getUsername(principal);
                String message = exceptionMessageGenerator.generateUserNotFoundMessage(username);

                throw new EntityNotFoundException(message);
            }
        } else {
            return new ResponseEntity<>(Map.of("Error", "Illegal path variables!"), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional()
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
