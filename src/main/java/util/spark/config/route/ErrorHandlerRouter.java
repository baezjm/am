package util.spark.config.route;

import util.spark.config.Application;
import com.google.gson.Gson;
import util.java.config.exception.APIException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.exception;
import static spark.Spark.notFound;

/**
 * Erro handler
 */
public class ErrorHandlerRouter {

    private final Logger log = LoggerFactory.getLogger(ErrorHandlerRouter.class);

    /**
     * Register basic error handle.
     * If you need custom handling extend this class and register it to the application module.
     */
    public void register() {
        exception(APIException.class, this::apiException);
        exception(Exception.class, this::genericError);

        notFound(this::handleNotFound);
    }

    /**
     * API Exception handler.
     *
     * @param e
     * @param request
     * @param response
     */
    protected void apiException(APIException e, Request request, Response response) {
        respond(response, getErrorResponse(e.getStatusCode(), e.getErrorCode(), e, !isPublic(request)));
    }

    /**
     * Generic Error
     *
     * @param e
     * @param request
     * @param response
     */
    protected void genericError(Exception e, Request request, Response response) {
        log.error("Responding with error", e);
        respond(response, getErrorResponse(500, "runtime_error", e, !isPublic(request)));
    }

    /**
     * Not found route
     *
     * @param req
     * @param res
     * @return
     */
    public Object handleNotFound(Request req, Response res) {
        return respond(res, new ErrorResponse(404, "not_found", req.url(), Application.getRequestId()));
    }


    /**
     * Respond with the json error.
     *
     * @param response
     * @param errorResponse
     * @return
     */
    protected String respond(Response response, ErrorResponse errorResponse) {
        response.status(errorResponse.statusCode);
        response.header("Content-Type", "application/json");
        response.header("request_id", errorResponse.requestId);
        final String json = getGson().toJson(errorResponse);
        response.body(json);
        return json;
    }

    /**
     * Find the header X-Public to know if we are called from public gateway.
     */
    protected Boolean isPublic(Request request) {
        String isPublicH = request.headers("X-Public");
        return "true".equals(isPublicH);
    }

    protected ErrorResponse getErrorResponse(int statusCode, String errorCode, Throwable error, Boolean showStackTrace) {
        return new ErrorResponse(statusCode, errorCode, error, showStackTrace, Application.getRequestId());
    }

    protected Gson getGson() {
        return Application.getInstance(Gson.class);
    }


    /**
     * Represent an Error Response based on an Exception.
     */
    @SuppressWarnings("squid:S1068")
    public static class ErrorResponse {

        private final Integer statusCode;
        private final String code;
        private final String message;
        private final String stacktrace;
        private final String requestId;

        public ErrorResponse(Integer statusCode, String errorCode, String errorMessage, String requestId) {
            this.statusCode = statusCode;
            this.message = errorMessage;
            this.code = errorCode;
            this.requestId = requestId;
            this.stacktrace = null;
        }

        /**
         * @param statusCode     Response status code
         * @param errorCode
         * @param exception      Exception to make the response
         * @param showStackTrace Display or not the stacktrace. For security issues if
         * @param requestId
         */
        public ErrorResponse(int statusCode, String errorCode, Throwable exception, Boolean showStackTrace, String requestId) {
            this.statusCode = statusCode;
            this.code = errorCode;
            this.message = exception.getMessage();
            this.requestId = requestId;
            this.stacktrace = showStackTrace ? ExceptionUtils.getStackTrace(exception) : null;
        }

    }
}
