package by.yatsukovich.controller.rest;

import by.yatsukovich.controller.dto.DraftResponseDto;
import by.yatsukovich.controller.dto.ResponseDto;
import by.yatsukovich.controller.mapper.AnswerViewMapper;
import by.yatsukovich.controller.mapper.ResponseMapper;
import by.yatsukovich.controller.request.PutResponseAnswersRequest;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.exception.EntityNotFoundException;
import by.yatsukovich.exception.ExceptionMessageGenerator;
import by.yatsukovich.exception.ValidationException;
import by.yatsukovich.security.util.PrincipalUtils;
import by.yatsukovich.security.util.ValidationUtils;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final PrincipalUtils principalUtils;

    private final UserService userService;

    private final ResponseService responseService;

    private final SurveyService surveyService;

    private final ResponseMapper responseMapper;

    private final AnswerViewMapper answerViewMapper;

    private final ExceptionMessageGenerator exceptionMessageGenerator;

    private final ValidationUtils validationUtils;


    @Transactional()
    @PostMapping("/surveys/{surveyId}")
    public ResponseEntity<Map<String, Object>> createDraftResponse(
            @PathVariable Long surveyId,
            @RequestParam String accessCodeword,
            Principal principal) {
        Map<String, Object> resultMap;

        if (!validationUtils.positive(surveyId)) {
            throw new ValidationException("Illegal surveyId");
        }

        Optional<User> optionalUser = principalUtils.getUserOptional(principal);

        if (optionalUser.isPresent()) {
            Response response = responseService.draftResponse(
                    principalUtils.getUsername(principal),
                    surveyId,
                    accessCodeword
            );
            DraftResponseDto draftedResponse = responseMapper.responseToDraftResponseDto(response);
            resultMap = Map.of("draftedResponse", draftedResponse);

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } else {
            resultMap = Map.of("Error", "User not found by principles !");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }

    @Transactional()
    @PutMapping("/{responseId}")
    public ResponseEntity<Map<String, Object>> putResponseAnswers(
            @PathVariable Long responseId,
            @Valid @RequestBody PutResponseAnswersRequest updateResponseRequest,
            BindingResult bindingResult,
            Principal principal) {

        Map<String, Object> resultMap;

        if (bindingResult.hasErrors()) {
            resultMap = bindingResult.getModel();
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        Optional<User> responderOptional = principalUtils.getUserOptional(principal);
        if (responderOptional.isPresent()) {
            Response response = responseService.getDraftedUserResponse(responderOptional.get(), responseId);
            //
            List<AnswerView> answerViews = answerViewMapper.answerCreateRequestsToAnswerViews(updateResponseRequest.getAnswerCreateRequests());
            response = responseService.validateAndSaveAnswers(response, updateResponseRequest.getSpentTime(), answerViews);
            //
            ResponseDto responseDto = responseMapper.responseToResponseDto(response);

            return new ResponseEntity<>(Map.of("response", responseDto), HttpStatus.OK);
        } else {
            String username = principalUtils.getUsername(principal);
            String message = exceptionMessageGenerator.generateUserNotFoundMessage(username);

            throw new EntityNotFoundException(message);
        }

    }

}
