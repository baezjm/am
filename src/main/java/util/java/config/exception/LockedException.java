package util.java.config.exception;

public class LockedException extends APIException {

    public static final String LOCKED = "locked";

    public LockedException(String errorMessage) {
        super(423, LOCKED, errorMessage);
    }
}