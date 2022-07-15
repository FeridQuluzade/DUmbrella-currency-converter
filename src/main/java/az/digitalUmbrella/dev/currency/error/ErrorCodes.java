package az.digitalUmbrella.dev.currency.error;

public enum ErrorCodes implements ErrorCode {

    INVALID_USERNAME_OR_PASSWORD,
    MISSING_MANDATORY_PARAMETER,
    ALREADY_TAKEN,
    FACULTY_NOT_FOUND,
    DEPARTMENT_NOT_FOUND,
    USER_NOT_FOUND;

    @Override
    public String code() {
        return this.name();
    }

}
