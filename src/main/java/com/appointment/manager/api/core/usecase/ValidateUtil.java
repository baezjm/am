package com.appointment.manager.api.core.usecase;

import com.google.inject.Singleton;
import spark.utils.Assert;

import java.util.List;

/**
 * This class is to reuse validation of parameters
 *
 */
@Singleton
public class ValidateUtil {

    /**
     * Validate if value is not null
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateNotNull(Object value, String name) {

        validateName(name);
        Assert.notNull(value, name.concat(" can not be null"));
    }

    /**
     * Validate if value is Higher than zero
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateHigherThanZero(Long value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(value >= 1, name.concat(" can not be less then 1"));
    }

    /**
     * Validate if integer value is Higher than zero
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateHigherThanZero(Integer value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(value >= 1, name.concat(" can not be less then 1"));
    }

    /**
     * Validate if long value is positive
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validatePositive(Integer value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(value >= 0, name.concat(" can not be less then 0"));
    }

    /**
     * Validate if long value is positive
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validatePositive(Long value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(value >= 0, name.concat(" can not be less then 0"));
    }

    /**
     * Validate if integer value is higher than or equals to 0
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateHigherThanOrEqualsZero(Integer value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(value >= 0, name.concat(" can not be less then 0"));
    }

    /**
     * Validate if a value of string object is filled
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateFilled(String value, String name) {

        validateNotNull(value, name);
        Assert.isTrue(!value.trim().isEmpty(), name.concat(" can not be empty"));
    }

    /**
     * Validate if a value of long object is filled
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateFilled(Long value, String name) {

        validateNotNull(value, name);
        validateHigherThanZero(value, name);
    }

    /**
     * Validate if a value of double object is filled
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateFilled(Double value, String name) {

        validateNotNull(value, name);
        validateHigherThanZero(value.longValue(), name);
    }

    /**
     * Validate if a value of integer object is filled
     *
     * @param value is the value that will be validate
     * @param name  is the name of value that will be validate
     */
    public void validateFilled(Integer value, String name) {

        validateNotNull(value, name);
        validateHigherThanZero(value, name);
    }

    /**
     * Validate if a list is not null and not empty
     *
     * @param list is the list that will be validate
     * @param name is the name of list that will be validate
     */
    public void validateFilled(List<?> list, String name) {

        validateNotNull(list, name);
        Assert.notEmpty(list.toArray(), name.concat(" can not be empty"));
    }

    /**
     * Validate if a name of string object is not null and not empty
     *
     * @param name is the name of value that will be validate
     */
    private void validateName(String name) {

        Assert.notNull(name, "Name parameter can not be null");
        Assert.isTrue(!name.trim().isEmpty(), "Name parameter can not be empty");
    }

    /**
     * Validate if value is Lower or Equals to the limit
     *
     * @param value is the value that will be validate
     * @param limit is the accepted max value
     * @param name  is the name of value that will be validate
     */
    public void validateLowerThanOrEqualsLimit(Long value, Long limit, String name) {

        validateNotNull(value, name);
        validateNotNull(limit, name.concat(" limit"));
        Assert.isTrue(value <= limit, name.concat(" can not be higher than " + limit));
    }
}
