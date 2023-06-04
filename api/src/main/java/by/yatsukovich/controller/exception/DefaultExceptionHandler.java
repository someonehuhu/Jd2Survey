package by.yatsukovich.controller.exception;

import by.yatsukovich.exception.EntityNotFoundException;
import by.yatsukovich.exception.ResponseDraftDeniedException;
import by.yatsukovich.exception.ValidationException;
import by.yatsukovich.util.RandomValuesGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

import static by.yatsukovich.controller.response.ApplicationErrorCodes.BAD_REQUEST;
import static by.yatsukovich.controller.response.ApplicationErrorCodes.ENTITY_NOT_FOUND_ERROR;
import static by.yatsukovich.controller.response.ApplicationErrorCodes.FATAL_ERROR;
import static by.yatsukovich.controller.response.ApplicationErrorCodes.RESPONSE_DRAFT_ERROR;


@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    private final RandomValuesGenerator generator;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleRuntimeException(EntityNotFoundException e) {

        //log.error(exceptionUniqueId + e.getMessage(), e);

        ErrorMessage errorMessage = new ErrorMessage(
                generator.uuidGenerator(),
                ENTITY_NOT_FOUND_ERROR.getCodeId(),
                e.getMessage()
        );

        return generateErrorResponse(errorMessage);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleRuntimeException(ValidationException e) {

        //log.error(exceptionUniqueId + e.getMessage(), e);

        ErrorMessage errorMessage = new ErrorMessage(
                generator.uuidGenerator(),
                RESPONSE_DRAFT_ERROR.getCodeId(),
                e.getMessage()
        );

        return generateBadRequestResponse(errorMessage);
    }

    @ExceptionHandler(ResponseDraftDeniedException.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleRuntimeException(ResponseDraftDeniedException e) {

        //log.error(exceptionUniqueId + e.getMessage(), e);

        ErrorMessage errorMessage = new ErrorMessage(
                generator.uuidGenerator(),
                RESPONSE_DRAFT_ERROR.getCodeId(),
                e.getMessage()
        );

        return generateNotAllowedResponse(errorMessage);
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleIllegalRequestException(IllegalRequestException e) {
        String exceptionUniqueId = generator.uuidGenerator();

        BindingResult bindingResult = e.getBindingResult();
        String collect = bindingResult
                .getAllErrors()
                .stream()
                .map(ObjectError::toString)
                .collect(Collectors.joining(","));

        //log.error(exceptionUniqueId + e.getMessage(), e);
        ErrorMessage errorMessage = new ErrorMessage(
                exceptionUniqueId,
                BAD_REQUEST.getCodeId(),
                collect
        );

        return generateBadRequestResponse(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleOthersException(Exception e) {
        /* Handles all other exceptions. Status code 500. */

        String exceptionUniqueId = generator.uuidGenerator();

        //log.error(exceptionUniqueId + e.getMessage(), e);

        ErrorMessage errorMessage = new ErrorMessage(
                exceptionUniqueId,
                FATAL_ERROR.getCodeId(),
                e.getMessage()
        );

        return generateErrorResponse(errorMessage);
    }

    private ResponseEntity<Map<String, ErrorMessage>> generateErrorResponse(ErrorMessage errorMessage) {
        return generateResponse("ERROR", errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, ErrorMessage>> generateBadRequestResponse(ErrorMessage errorMessage) {
        return generateResponse("Bad request", errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, ErrorMessage>> generateNotAllowedResponse(ErrorMessage errorMessage) {
        return generateResponse("Not allowed", errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }


    private ResponseEntity<Map<String, ErrorMessage>> generateResponse(
            String key, ErrorMessage errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(Map.of(key, errorMessage), httpStatus);
    }

}
