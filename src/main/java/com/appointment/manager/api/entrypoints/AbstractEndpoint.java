package com.appointment.manager.api.entrypoints;

import com.appointment.manager.api.core.InstanceNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import spark.Request;
import spark.Response;
import util.java.config.exception.*;
import util.spark.config.Application;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This abstract class standardize and promote reuse of entrypoints commands.
 *
 * @see Gson
 * @see Request
 * @see Response
 * @see ContentType
 * @see HTTP
 * @see JsonSyntaxException
 * @see NotFoundException
 * @see BadRequestException
 * @see InternalServerErrorException
 */
public abstract class AbstractEndpoint<T> {

    @Inject
    public Gson gson;

    /**
     * Defines the default execution flow for all entrypoints,
     * encapsulating all possible exceptions. By default, will sign the response
     * with {@code HttpStatus.SC_OK} and contentType {@code ContentType.APPLICATION_JSON}.
     *
     * @param request
     * @param response
     * @return Response model
     * @throws Exception
     */
    public T execute(Request request, Response response) {
        try {
            signResponse(response, HttpStatus.SC_OK, ContentType.APPLICATION_JSON);
            return doExecute(request, response);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        } catch (BadRequestException | NotFoundException | InternalServerErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Entrypoint execution
     *
     * @param request  Request
     * @param response Response
     * @return Response model
     * @throws Exception
     */
    abstract protected T doExecute(Request request, Response response) throws Exception;

    /**
     * Sign the response setting the header type, response type and status code
     *
     * @param response
     * @param statusCode
     * @param contentType
     */
    protected void signResponse(Response response, int statusCode, ContentType contentType) {

        response.header(HTTP.CONTENT_TYPE, contentType.getMimeType());
        response.type(contentType.getMimeType());
        response.status(statusCode);

    }

    /**
     * Parse the resquest body to any class you need
     *
     * @param request
     * @param type
     * @param <B>
     * @return
     */
    protected <B> B parseBody(Request request, Type type) {

        try {
            return gson.fromJson(request.body(), type);
        } catch (JsonSyntaxException e) {
            throw new BadRequestException("Invalid body data: " + request.body());
        }
    }

    /**
     * Returns the value of the provided route pattern parameter from a specific request
     *
     * @param request the request to get the path param
     * @param param   the param
     * @return null if the given param is null or not found
     */
    protected String getPathParam(Request request, String param) {

        if (!param.startsWith(":"))
            param = ":" + param;

        return request.params(param);
    }

    /**
     * Returns the Integer value of the provided route pattern parameter from a specific request
     *
     * @param request the request to get the path param
     * @param param   the param
     * @return null if the given param is null or not found
     */
    protected Integer getPathParamAsInteger(Request request, String param) {

        String value = getPathParam(request, param);
        return getInteger(value);
    }

    /**
     * Returns the Long value of the provided route pattern parameter from a specific request
     *
     * @param request the request to get the path param
     * @param param   the param
     * @return null if the given param is null or not found
     */
    protected Long getPathParamAsLong(Request request, String param) {

        String value = getPathParam(request, param);
        return getLong(value);
    }

    /**
     * Gets the query param from a specific request
     *
     * @param request    the request to get the query param
     * @param queryParam the query parameter
     * @return the value of the provided queryParam
     */
    protected String getQueryParam(Request request, String queryParam) {
        return request.queryParams(queryParam);
    }

    /**
     * Gets the Integer query param value from a specific request
     *
     * @param request    the request to get the query param
     * @param queryParam the query parameter
     * @return the Integer value of the provided queryParam
     */
    protected Integer getQueryParamAsInteger(Request request, String queryParam) {

        String value = getQueryParam(request, queryParam);
        return getInteger(value);
    }

    /**
     * Gets the Date query param value from a specific request
     *
     * @param request    the request to get the query param
     * @param queryParam the query parameter
     * @return the Integer value of the provided queryParam
     */
    protected Date getQueryParamAsDate(Request request, String queryParam) {

        String value = getQueryParam(request, queryParam);
        return getDate(value);
    }

    /**
     * Gets the Long query param value from a specific request
     *
     * @param request    the request to get the query param
     * @param queryParam the query parameter
     * @return the Long value of the provided queryParam
     */
    protected Long getQueryParamAsLong(Request request, String queryParam) {

        String value = getQueryParam(request, queryParam);
        return getLong(value);
    }

    /**
     * Returns the Enum of the provided route pattern parameter from a specific request
     *
     * @param request the request to get the path param
     * @param param   the param
     * @param clazz   Enum Class
     * @return null if the given param is null or not found
     */
    protected <E extends Enum<E>> E getQueryParamAsEnum(Request request, String param, Class<E> clazz) {

        E value = null;

        String valueString = getQueryParam(request, param);

        if (valueString != null) {
            try {
                value = Enum.valueOf(clazz, valueString);
            } catch (IllegalArgumentException e) {
                String message = "Invalid value for enum " + clazz.getSimpleName() + "." + clazz.getSimpleName() +": " + param;
                throw new BadRequestException(message);
            }
        }

        return value;
    }

    /**
     * Parses the string argument as a Long.
     *
     * @param paramValue a {@code String} containing the {@code Long}
     *                   representation to be parsed
     * @return the Long value represented by the argument in decimal.
     * @throws BadRequestException if the string does not contain a
     *                             parsable Long.
     */
    private Long getLong(String paramValue) {

        Long value = null;

        if (paramValue != null) {
            try {
                value = Long.parseLong(paramValue);
            } catch (NumberFormatException ex) {
                throw new BadRequestException("Invalid data: " + paramValue);
            }
        }

        return value;

    }

    /**
     * Parses the string argument as a Integer.
     *
     * @param paramValue a {@code String} containing the {@code Integer}
     *                   representation to be parsed
     * @return the Integer value represented by the argument in decimal.
     * @throws BadRequestException if the string does not contain a
     *                             parsable Integer.
     */
    private Integer getInteger(String paramValue) {

        Integer value = null;

        if (paramValue != null) {
            try {
                value = Integer.parseInt(paramValue);
            } catch (NumberFormatException ex) {
                throw new BadRequestException("Invalid data: " + paramValue);
            }
        }

        return value;
    }

    /**
     * Parses the string argument as a Integer.
     *
     * @param paramValue a {@code String} containing the {@code Integer}
     *                   representation to be parsed
     * @return the Integer value represented by the argument in decimal.
     * @throws BadRequestException if the string does not contain a
     *                             parsable Integer.
     */
    private Date getDate(String paramValue) {

        Date value = null;

        if (paramValue != null) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                value = format.parse(paramValue);

            } catch (NumberFormatException | ParseException ex) {
                throw new BadRequestException("Invalid data: " + paramValue);
            }
        }

        return value;
    }


    /**
     * Get instance from the subscribed injector.
     *
     * @param clazz
     * @param <R>
     * @return the Class of this application.
     */
    protected <R> R getInstance(Class<R> clazz) {

        R instance = Application.getInstance(clazz);

        if (instance == null)
            throw new InstanceNotFoundException(clazz);

        return instance;
    }

    /**
     * Parse string to enum
     *
     * @param valueString value as string
     * @param clazz       enum class type
     * @param <E>         enum
     * @return parsed enum
     */
    protected <E extends Enum<E>> E parseEnum(String valueString, Class<E> clazz) {

        E value = null;

        if (valueString != null) {
            try {
                value = Enum.valueOf(clazz, valueString);
            } catch (IllegalArgumentException e) {
                String message = "Invalid value for enum " + clazz.getSimpleName() + ": " + valueString;
                throw new BadRequestException(message);
            }
        }

        return value;
    }

}