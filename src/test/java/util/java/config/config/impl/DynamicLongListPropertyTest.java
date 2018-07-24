package util.java.config.config.impl;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DynamicLongListPropertyTest {
    @Test
    public void from() throws Exception {
        final Long value = new DynamicLongListProperty("a.key", Collections.emptyList()).from("123");
        final Long negativeValue = new DynamicLongListProperty("a.key", Collections.emptyList()).from("-123");
        assertEquals(123L, (long) value);
        assertEquals(-123L, (long) negativeValue);
    }

}
