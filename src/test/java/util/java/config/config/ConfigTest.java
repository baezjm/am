package util.java.config.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ConfigTest {

    @After
    public void tearDown() {
        Config.clearInjectors();
    }

    @Test
    public void testRemoveInjector() {
        Config.addInjector("module", Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

            }
        }));
        Config.clearInjector("module");

        assertEquals(new Integer(0), new Integer(Config.injectors.size()));
    }

    @Test
    public void testSafeGetInstance() {
        Config.addInjector("module", Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).toInstance("JRR");
            }
        }));

        assertNotNull(Config.safeGetInstance("module", String.class));

        Supplier supplier = Config.safeGetInstance("module", Supplier.class);

        assertNull(supplier);

        Config.clearInjectors();

        assertNull(Config.safeGetInstance("module", Integer.class));
    }

    @Test
    public void testSafeGetInstanceWithKey() {
        Config.addInjector("module", Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named("T1")).toInstance("JRR1");
                bind(String.class).annotatedWith(Names.named("T2")).toInstance("JRR2");
            }
        }));

        String instance1 = Config.safeGetInstance("module", Key.get(String.class, Names.named("T1")));
        String instance2 = Config.safeGetInstance("module", Key.get(String.class, Names.named("T2")));

        assertEquals(instance1,"JRR1");
        assertEquals(instance2,"JRR2");

        Supplier supplier = Config.safeGetInstance("module", Key.get(Supplier.class, Names.named("S")));

        assertNull(supplier);

        Config.clearInjectors();

        assertNull(Config.safeGetInstance("module", Key.get(Supplier.class, Names.named("S"))));

    }

    @Test
    public void getBigDecimal() throws Exception {
        final BigDecimal aDefault = Config.get().getBigDecimal("una.key.de.configuracion", new BigDecimal(10));
        assertEquals(new BigDecimal(10), aDefault);
    }

    @Test
    public void addInjector() throws Exception {
        Config.get().addInjector("module", Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

            }
        }));
        assertNotNull(Config.getInjector("module"));
    }

    @Test
    public void getString() throws Exception {
        final String aDefault = Config.get().getString("una.key.de.configuracion", "default");
        assertEquals("default", aDefault);
    }

    @Test
    public void getInt() throws Exception {
        final Integer aDefault = Config.get().getInt("una.key.de.configuracion", 10);
        assertEquals(new Integer(10), aDefault);
    }

    @Test
    public void getLong() throws Exception {
        final Long aDefault = Config.get().getLong("una.key.de.configuracion", 10L);
        assertEquals(new Long(10), aDefault);
    }

    @Test
    public void getBoolean() throws Exception {
        final Boolean aDefault = Config.get().getBoolean("una.key.de.configuracion", false);
        assertFalse(aDefault);
    }

    @Test
    public void getStringList() throws Exception {
        final List aDefault = Config.get().getStringList("una.key.de.configuracion", Arrays.asList("first"));
        assertEquals(1, aDefault.size());
        assertEquals("first", aDefault.get(0));
    }


    @Test
    public void getLongList() throws Exception {
        final List<Long> aDefault = Config.get().getLongList("una.key.de.configuracion", Arrays.asList(1L));
        assertEquals(1, aDefault.size());
        assertEquals(1L, (long) aDefault.get(0));
    }


    @Test
    public void testNulls() throws Exception {
        assertNull(Config.get().getString("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getLong("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getBigDecimal("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getBoolean("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getInt("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getLongList("una.key.q.no.existe.ni.loco", null));
        assertNull(Config.get().getStringList("una.key.q.no.existe.ni.loco", null));
    }
}
