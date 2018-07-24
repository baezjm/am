package util.java.config.exception;

public class ConflictException extends APIException {

    public static final String CONFLICT = "conflict";

    public ConflictException(String errorMessage) {
        super(409, CONFLICT, errorMessage);
    }

    public ConflictException(String errorMessage, Throwable cause) {
        super(errorMessage, cause, 409, CONFLICT);
    }

    public ConflictException(Throwable cause) {
        super(cause, 409, CONFLICT);
    }
}
