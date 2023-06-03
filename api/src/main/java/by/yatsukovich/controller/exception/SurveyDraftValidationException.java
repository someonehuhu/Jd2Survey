package by.yatsukovich.controller.exception;

public class SurveyDraftValidationException extends RuntimeException {

    public SurveyDraftValidationException(DraftDeniedMessage draftDeniedMessage) {
        super("Failed to draft response on survey.\nCause : " + draftDeniedMessage.name());
    }

}
