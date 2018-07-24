package util.java.config.exception;

public class NotAcceptableException extends APIException {

    public static final String NOT_ACCEPTABLE = "internal_server_error";

    public NotAcceptableException(String errorMessage) {
        super(406, NOT_ACCEPTABLE, errorMessage);
    }

}
