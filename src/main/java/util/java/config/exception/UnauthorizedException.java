package util.java.config.exception;

public class UnauthorizedException extends APIException{

    public static final String UNAUTHORIZED = "unauthorized";

    public UnauthorizedException(String errorMessage) {
        super(401, UNAUTHORIZED, errorMessage);
    }

}
