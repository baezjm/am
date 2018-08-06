package com.appointment.manager.api.helper;


import util.java.config.config.Config;
import util.java.config.config.impl.ArchaiusConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppdcruz on 14/03/18.
 */
public class FakeConfig extends ArchaiusConfig {

    private Map<String, Object> values;

    private FakeConfig() {
        values = new HashMap<>();
    }

    private static FakeConfig fakeConfig;

    public static void clear() {

        FakeConfig fakeConfig = getFakeConfig();

        fakeConfig.values.clear();
    }

    public static void put(String key, Object value) {

        FakeConfig fakeConfig = getFakeConfig();

        fakeConfig.values.put(key, value);
    }

    private static FakeConfig getFakeConfig() {

        if (fakeConfig == null) {
            init();
        }

        return fakeConfig;
    }

    private static void init() {

        fakeConfig = new FakeConfig();
        Config.setImplementation(fakeConfig);

    }

    @Override
    protected Integer doGetInt(String key, Integer def) {

        Integer value = (Integer) values.get(key);
        if (value != null) {
            return value;
        }

        return def;
    }

    @Override
    protected String doGetString(String key, String def) {

        String value = (String) values.get(key);
        if (value != null) {
            return value;
        }

        return def;
    }

    @Override
    protected Boolean doGetBoolean(String key, Boolean def) {

        Boolean value = (Boolean) values.get(key);
        if (value != null) {
            return value;
        }

        return def;
    }

    @Override
    protected Long doGetLong(String key, Long def) {

        Long value = (Long) values.get(key);
        if (value != null) {
            return value;
        }

        return def;
    }
}