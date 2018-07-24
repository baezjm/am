package util.java.config.config.impl;

import com.google.inject.Singleton;
import com.netflix.config.*;
import util.java.config.config.Config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 *
 */
@Singleton
public class ArchaiusConfig extends Config {

    private Map<String, DynamicStringListProperty> stringListCache = new ConcurrentHashMap<>();
    private Map<String, DynamicLongListProperty> longListCache = new ConcurrentHashMap<>();
    private Map<String, DynamicStringProperty> bigDecimalCache = new ConcurrentHashMap<>();
    private Map<String, DynamicStringProperty> stringCache = new ConcurrentHashMap<>();
    private Map<String, DynamicIntProperty> intCache = new ConcurrentHashMap<>();
    private Map<String, DynamicBooleanProperty> booleanCache = new ConcurrentHashMap<>();
    private Map<String, DynamicLongProperty> longCache = new ConcurrentHashMap<>();

    @Override
    protected BigDecimal doGetBigDecimal(String key, BigDecimal def) {
        final String d = withCache(bigDecimalCache, () -> DynamicPropertyFactory.getInstance().getStringProperty(key, def == null ? null : def.toString()), key).get();
        return (d == null) ? null : new BigDecimal(d);
    }

    @Override
    protected String doGetString(String key, String def) {
        return withCache(stringCache, () -> DynamicPropertyFactory.getInstance().getStringProperty(key, def), key).get();
    }


    @Override
    protected Integer doGetInt(String key, Integer def) {
        int primitive = def == null ? -1 : def;
        final int i = withCache(intCache, () -> DynamicPropertyFactory.getInstance().getIntProperty(key, primitive), key).get();
        return (def == null && primitive == i) ? null : i;
    }

    @Override
    protected Boolean doGetBoolean(String key, Boolean def) {
        boolean primitive = def == null ? false : def;
        final boolean b = withCache(booleanCache, () -> DynamicPropertyFactory.getInstance().getBooleanProperty(key, primitive), key).get();
        return (def == null && primitive == b) ? null : new Boolean(b);
    }

    @Override
    protected Long doGetLong(String key, Long def) {
        long primitive = def == null ? -1l : def;
        final long l = withCache(longCache, () -> DynamicPropertyFactory.getInstance().getLongProperty(key, primitive), key).get();
        return (def == null && primitive == l) ? null : new Long(l);
    }

    @Override
    protected List<String> doGetStringList(String key, List<String> def) {
        return withCache(stringListCache, () -> new DynamicStringListProperty(key, def), key).get();
    }

    @Override
    protected List<Long> doGetLongList(String key, List<Long> def) {
        return withCache(longListCache, () -> new DynamicLongListProperty(key, def), key).get();
    }

    /**
     * Obtains a property from cache/supplier
     *
     * @param cache
     * @param supplier
     * @param key
     * @param <Prop>
     * @return Property
     */
    <Prop> Prop withCache(Map<String, Prop> cache, Supplier<Prop> supplier, String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        final Prop prop = supplier.get();
        cache.put(key, prop);
        return prop;
    }
}
