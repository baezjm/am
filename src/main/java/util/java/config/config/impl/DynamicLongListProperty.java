package util.java.config.config.impl;

import com.google.common.base.Splitter;
import com.netflix.config.DynamicListProperty;

import java.util.List;

/**
 * Implementation of a Dynamic List for Longs
 * <p>
 *
 */
public class DynamicLongListProperty extends DynamicListProperty<Long> {

    public DynamicLongListProperty(String propName, String defaultValue) {
        super(propName, defaultValue);
    }

    public DynamicLongListProperty(String propName, List<Long> defaultValue) {
        super(propName, defaultValue);
    }

    public DynamicLongListProperty(String propName, String defaultValue, String listDelimiterRegex) {
        super(propName, defaultValue, listDelimiterRegex);
    }

    public DynamicLongListProperty(String propName, List<Long> defaultValue, String listDelimiterRegex) {
        super(propName, defaultValue, listDelimiterRegex);
    }

    public DynamicLongListProperty(String propName, List<Long> defaultValue, Splitter splitter) {
        super(propName, defaultValue, splitter);
    }

    @Override
    protected Long from(String value) {
        return Long.valueOf(value);
    }
}
