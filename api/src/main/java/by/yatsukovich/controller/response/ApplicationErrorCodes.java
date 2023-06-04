package by.yatsukovich.controller.response;

public enum ApplicationErrorCodes {
    ENTITY_NOT_FOUND_ERROR(10),
    BAD_REQUEST(15),

    RESPONSE_DRAFT_ERROR(15),

    VALIDATION_ERROR(16),

    FATAL_ERROR(1);

    private final int codeId;

    public int getCodeId() {
        return codeId;
    }

    ApplicationErrorCodes(int codeId) {
        this.codeId = codeId;
    }
}
