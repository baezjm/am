package com.appointment.manager.api.core.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ValidateUtilTest {

    private ValidateUtil validateUtil;

    @Before
    public void setup() {
        validateUtil = new ValidateUtil();
    }

    @Test
    public void filled_withoutName() {

        String name = null;
        Long value = 1L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_withNameEmpty() {

        String name = "";
        Long value = 1L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_withoutLongValue() {

        String name = "Name";
        Long value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_longValueZero() {

        String name = "Name";
        Long value = 0L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_longValue_success() {

        String name = "Name";
        Long value = 1L;

        assertThatCode(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_withoutIntegerValue() {

        String name = "Name";
        Integer value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_integerValueZero() {

        String name = "Name";
        Integer value = 0;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_integerValue_success() {

        String name = "Name";
        Integer value = 1;

        assertThatCode(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_withoutDoubleValue() {

        String name = "Name";
        Double value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_doubleValueZero() {

        String name = "Name";
        Double value = 0D;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_doubleNegativeValue() {

        String name = "Name";
        Double value = -10.5D;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_doubleValue_success() {

        String name = "Name";
        Double value = 10.5D;

        assertThatCode(() -> {
            validateUtil.validateFilled(value, name);
        }).doesNotThrowAnyException();
    }

    @Test
    public void filled_stringWithoutValue() {

        String name = "Name";
        String value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_stringEmptyValue() {

        String name = "Name";
        String value = " ";

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(value, name);
        });
    }

    @Test
    public void filled_stringValue_success() {

        String name = "Name";
        String value = "Value";

        assertThatCode(() -> {
            validateUtil.validateFilled(value, name);
        }).doesNotThrowAnyException();
    }

    @Test
    public void validatePositive_longValueNegative() {

        String name = "Name";
        Long value = -1L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validatePositive(value, name);
        });

    }

    @Test
    public void validatePositive_longValueZero_success() {

        String name = "Name";
        Long value = 0L;

        assertThatCode(() -> {
            validateUtil.validatePositive(value, name);
        }).doesNotThrowAnyException();

    }

    @Test
    public void validatePositive_longValueOne_success() {

        String name = "Name";
        Long value = 1L;

        assertThatCode(() -> {
            validateUtil.validatePositive(value, name);
        }).doesNotThrowAnyException();

    }

    @Test
    public void validatePositive_intValueNegative() {

        String name = "Name";
        Integer value = -1;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validatePositive(value, name);
        });

    }

    @Test
    public void validatePositive_intValueZero_success() {

        String name = "Name";
        Integer value = 0;

        assertThatCode(() -> {
            validateUtil.validatePositive(value, name);
        }).doesNotThrowAnyException();

    }

    @Test
    public void validatePositive_intValueOne_success() {

        String name = "Name";
        Integer value = 1;

        assertThatCode(() -> {
            validateUtil.validatePositive(value, name);
        }).doesNotThrowAnyException();

    }

    @Test
    public void validatePositive_withoutIntegerValue() {

        String name = "Name";
        Integer value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanOrEqualsZero(value, name);
        });

    }

    @Test
    public void validatePositive_integerValueNegative() {

        String name = "Name";
        Integer value = -1;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanOrEqualsZero(value, name);
        });

    }

    @Test
    public void validatePositive_integerValueZero_success() {

        String name = "Name";
        Integer value = 0;

        assertThatCode(() -> {
            validateUtil.validateHigherThanOrEqualsZero(value, name);
        }).doesNotThrowAnyException();

    }

    @Test
    public void validateNotNull_withoutValue() {
        String name = "Name";
        Long value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateNotNull(value, name);
        });
    }

    @Test
    public void validateNotNull_withValue_success() {

        String name = "Name";
        Long value = 1L;

        assertThatCode(() -> {
            validateUtil.validateNotNull(value, name);
        }).doesNotThrowAnyException();
    }

    @Test
    public void validateHigherThanZero_withoutValue() {

        String name = "Name";
        Long value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateHigherThanZero_withNegativeValue() {

        String name = "Name";
        Long value = -1L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateHigherThanZero_withZeroValue() {

        String name = "Name";
        Long value = 0L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateHigherThanZero_success() {

        String name = "Name";
        Long value = 10L;

        assertThatCode(() -> {
            validateUtil.validateHigherThanZero(value, name);
        }).doesNotThrowAnyException();
    }

    @Test
    public void validateHigherThanZero_withoutIntegerValue() {

        String name = "Name";
        Integer value = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateFilled_ListEmpty() {

        String name = "List";
        List<?> listEmpty = new ArrayList<>();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(listEmpty, name);
        });
    }

    @Test
    public void validateHigherThanZero_withNegativeIntegerValue() {

        String name = "Name";
        Integer value = -1;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateFilled_ListNull() {

        String name = "List";
        List<?> listEmpty = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateFilled(listEmpty, name);
        });
    }

    @Test
    public void validateFilled_ListNotEmpty() {

        String name = "List";

        List<String> listEmpty = new ArrayList<String>() {{
            add("String");
        }};

        assertThatCode(() -> {
            validateUtil.validateFilled(listEmpty, name);
        });
    }

    @Test
    public void validateLowerThanOrEqualsLimit_limitBelow() {
        String name = "Name";
        Long value = 10L;
        Long limit = 1L;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateLowerThanOrEqualsLimit(value, limit, name);
        });
    }

    @Test
    public void validateHigherThanZero_withZeroIntegerValue() {

        String name = "Name";
        Integer value = 0;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            validateUtil.validateHigherThanZero(value, name);
        });
    }

    @Test
    public void validateHigherThanZero_successIntegerValue() {

        String name = "Name";
        Integer value = 10;

        assertThatCode(() -> {
            validateUtil.validateHigherThanZero(value, name);
        }).doesNotThrowAnyException();
    }

    @Test
    public void validateLowerThanOrEqualsLimit_success() {
        String name = "Name";
        Long value = 10L;
        Long limit = 10L;

        assertThatCode(() -> {
            validateUtil.validateLowerThanOrEqualsLimit(value, limit, name);
        }).doesNotThrowAnyException();
    }
}