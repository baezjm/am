package util.java.config.exception;

public class ForbiddenException extends APIException{

    public static final String FORBIDDEN = "forbidden";

    public ForbiddenException(String errorMessage) {
        super(403, FORBIDDEN, errorMessage);
    }


}
