package by.yatsukovich.exception;

public class ExceptionMessageGenerator {


    public String generateEntityNotFoundMessage(String entityName, Long id) {
        return "Entity with name " +
                entityName +
                " not found by id = " +
                id;
    }

    public String generateUserNotFoundMessage(String username) {
        return "User with name " +
                username +
                " not found.";
    }

    public String generateDraftDeniedMessage(DraftDeniedCause cause) {
        return "Draft response for survey denied. Cause : " + cause.name();
    }

    public String generateMandatoryValidationMessage(Long questionId) {
        return "Question with id = " + questionId + " mandatory validation failed.";
    }
}
