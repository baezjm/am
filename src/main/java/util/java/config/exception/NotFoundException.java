package util.java.config.exception;

public class NotFoundException extends APIException {

    public static final String NOT_FOUND = "not_found";

    public NotFoundException(String errorMessage) {
        super(404, NOT_FOUND, errorMessage);
    }

}
