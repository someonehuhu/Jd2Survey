package by.yatsukovich.controller.rest;

import by.yatsukovich.controller.dto.DraftResponseDto;
import by.yatsukovich.controller.dto.ResponseDto;
import by.yatsukovich.controller.mapper.AnswerViewMapper;
import by.yatsukovich.controller.mapper.ResponseMapper;
import by.yatsukovich.controller.request.DraftResponseRequest;
import by.yatsukovich.controller.request.PutResponseAnswersRequest;
import by.yatsukovich.domain.hibernate.Response;
import by.yatsukovich.domain.hibernate.User;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import by.yatsukovich.security.util.PrincipalUtils;
import by.yatsukovich.service.ResponseService;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @PostMapping()
    public ResponseEntity<Map<String, Object>> createDraftResponse(
            @Valid @RequestBody DraftResponseRequest draftResponseRequest,
            BindingResult result,
            Principal principal) {
        Map<String, Object> resultMap;

        if (result.hasErrors()) {
            resultMap = result.getModel();
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = principalUtils.getUserOptional(principal);

        if (optionalUser.isPresent()) {
            Response response = responseService.draftResponse(
                    principalUtils.getUsername(principal),
                    draftResponseRequest.getSurveyId(),
                    draftResponseRequest.getAccessCodeword()
            );
            DraftResponseDto draftedResponse = responseMapper.responseToDraftResponseDto(response);
            resultMap = Map.of("draftedResponse", draftedResponse);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } else {
            resultMap = Map.of("Error", "User not found by principles !");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }

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
            List<AnswerView> answerViews = answerViewMapper.answerCreateRequestsToAnswerView(updateResponseRequest.getAnswerCreateRequests());
            response = responseService.saveResponseAnswers(response, updateResponseRequest.getSpentTime(), answerViews);
            //
            ResponseDto responseDto = responseMapper.responseToResponseDto(response);

            return new ResponseEntity<>(Map.of("response", responseDto), HttpStatus.OK);
        } else {
            throw new RuntimeException("Responder not found!");
        }

    }

}
