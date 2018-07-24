package util.java.config.exception;

import util.spark.config.Application;

public class APIException extends RuntimeException {

    private final Integer statusCode;
    private final String errorCode;
    private final String requestId;


    /**
     * Represent a not successful response.
     *
     * @param statusCode
     * @param errorCode
     * @param errorMessage
     */
    public APIException(Integer statusCode, String errorCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.requestId = Application.getRequestId();
    }


    /**
     * Represent a not successful response.
     *
     * @param message
     * @param cause
     * @param statusCode
     * @param errorCode
     */
    public APIException(String message, Throwable cause, Integer statusCode, String errorCode) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.requestId = Application.getRequestId();
    }

    /**
     * Represent a not successful response.
     *
     * @param cause
     * @param statusCode
     * @param errorCode
     */
    public APIException(Throwable cause, Integer statusCode, String errorCode) {
        super(cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.requestId = Application.getRequestId();
    }

    /**
     * @return the status code to return in the response.
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @return the error code to return in the response.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the request id that throw this globalconfig.java.util.config.exception.
     */
    public String getRequestId() {
        return requestId;
    }
}
