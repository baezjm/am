package util.java.config.config;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import util.java.config.config.impl.ArchaiusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

public abstract class Config {

    final static ConcurrentMap<String, Injector> injectors = new ConcurrentHashMap<>();
    static Config implementation;

    static {
        setImplementation(new ArchaiusConfig());
    }

    final Logger log = LoggerFactory.getLogger(Config.class);
    final String environmentScope;

    public Config() {
        environmentScope = RuntimeEnvironment.get().getScope();
    }

    public static void setImplementation(Config implementation) {
        Config.implementation = implementation;
    }

    public static void addInjector(String name, Injector injector) {
        injectors.put(name, injector);
    }

    public static Injector getInjector(String name) {
        return injectors.get(name);
    }

    /**
     * Safe get instance from the subscribed injector.
     *
     * @param injectorName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T safeGetInstance(String injectorName, Class<T> clazz) {
        try {
            final Injector injector = getInjector(injectorName);
            if (injector == null) return null;
            return injector.getInstance(clazz);
        } catch (ConfigurationException ex) {
            return null;
        }
    }


    /**
     * Safe get instance from the subscribed injector.
     *
     * @param injectorName
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T safeGetInstance(String injectorName, Key<T> key) {
        try {
            final Injector injector = getInjector(injectorName);
            if (injector == null) return null;
            return injector.getInstance(key);
        } catch (ConfigurationException ex) {
            return null;
        }
    }

    /**
     * Remove named injector.
     */
    public static void clearInjector(String name) {
        injectors.remove(name);
    }

    /**
     * Remove all injectors.
     */
    public static void clearInjectors() {
        injectors.clear();
    }

    public static Config get() {
        return implementation;
    }

    /**
     * Add the environmentScope to the key.
     *
     * @param key
     * @return
     */
    private String getKeyWithScope(String key) {
        return environmentScope + "." + key;
    }

    /**
     * Get string configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public String getString(String key, String def) {
        return doGetWithDefault(key, def, this::doGetString);
    }

    /**
     * Get int configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public Integer getInt(String key, Integer def) {
        return doGetWithDefault(key, def, this::doGetInt);
    }

    /**
     * Get int configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public Long getLong(String key, Long def) {
        return doGetWithDefault(key, def, this::doGetLong);
    }

    /**
     * Get boolean configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public Boolean getBoolean(String key, Boolean def) {
        return doGetWithDefault(key, def, this::doGetBoolean);
    }


    /**
     * Get BigDecimal configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public BigDecimal getBigDecimal(String key, BigDecimal def) {
        return doGetWithDefault(key, def, this::doGetBigDecimal);
    }

    /**
     * Get List configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public List<String> getStringList(String key, List<String> def) {
        return doGetWithDefault(key, def, this::doGetStringList);
    }

    /**
     * Get List configuration for key.
     * Order: Scoped key configuration, then key configuration, then default.
     *
     * @param key
     * @param def
     * @return
     */
    public List<Long> getLongList(String key, List<Long> def) {
        return doGetWithDefault(key, def, this::doGetLongList);
    }

    /**
     * @param key
     * @param def
     * @param function
     * @param <T>
     * @return
     */
    protected <T> T doGetWithDefault(String key, T def, BiFunction<String, T, T> function) {

        Object result = function.apply(getKeyWithScope(key), def);
        Object returnValue = ((def != null && !def.equals(result)) ? result : function.apply(key, def));

        log.trace("Configuration value for key: " + key + " = " + returnValue);

        return (T) returnValue;
    }

    protected abstract BigDecimal doGetBigDecimal(String key, BigDecimal def);

    protected abstract String doGetString(String key, String def);

    protected abstract Integer doGetInt(String key, Integer def);

    protected abstract Boolean doGetBoolean(String key, Boolean def);

    protected abstract Long doGetLong(String keyWithScope, Long def);

    protected abstract List<String> doGetStringList(String keyWithScope, List<String> def);

    protected abstract List<Long> doGetLongList(String keyWithScope, List<Long> def);
}
