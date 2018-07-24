package util.java.config.config;

import org.junit.Test;

import static org.junit.Assert.*;


public class RuntimeEnvironmentTest {

    @Test
    public void testGetEnv() {
        assertEquals("capo", RuntimeEnvironment.getenv("JRRD10S", "capo"));
        assertNotEquals("capo", RuntimeEnvironment.getenv("PATH", "capo"));
    }

    @Test
    public void testIsDevelopment() {
        assertTrue(RuntimeEnvironment.get().isDevelop());
        RuntimeEnvironment runtime = new RuntimeEnvironment("app", "read");
        assertFalse(runtime.isDevelop());
    }

    @Test
    public void testGets() {
        assertEquals("dev", RuntimeEnvironment.get().getScope());
        assertEquals("dev", RuntimeEnvironment.get().getApplication());

        RuntimeEnvironment runtime = new RuntimeEnvironment("app", "read");
        assertEquals("read", runtime.getScope());
        assertEquals("app", runtime.getApplication());
    }

    @Test
    public void isProduction() throws Exception {
        assertFalse(RuntimeEnvironment.get().isProduction());
        RuntimeEnvironment runtime = new RuntimeEnvironment("app", "prod");
        assertTrue(runtime.isProduction());
    }

}