package util.java.config.config;

import java.util.Arrays;
import java.util.List;

public class RuntimeEnvironment {

    private static final String PRODUCTION_SCOPES = "prod.scopes";
    private static final List<String> DEFAULT_PRODUCTION = Arrays.asList("prod");

    private static final String APPLICATION = "APPLICATION";
    private static final String SCOPE = "SCOPE";
    private static final String DEVELOPMENT = "dev";

    private final String scope;
    private final String application;

    /**
     * Create a runtime environment.
     *
     * @param application
     * @param scope
     */
    protected RuntimeEnvironment(String application, String scope) {
        this.scope = scope;
        this.application = application;
    }

    /**
     * @return the environment in runtime.
     */
    public static RuntimeEnvironment get() {
        return new RuntimeEnvironment(getenv(APPLICATION, DEVELOPMENT), getenv(SCOPE, DEVELOPMENT));
    }

    /**
     * Return the value of the specified environment variable. An
     * environment variable is a system-dependent external named
     * value.
     * <p>
     * If not present return the default value
     *
     * @param var
     * @param defaultValue
     * @return
     */
    public static String getenv(String var, String defaultValue) {
        final String env = System.getenv(var);
        if (env == null)
            return defaultValue;
        return env;
    }

    /**
     * @return the runtime globalconfig.spark.util.java.util.config.config.app.
     */
    public String getApplication() {
        return application;
    }

    /**
     * @return the runtime scope.
     */
    public String getScope() {
        return scope;
    }

    /**
     * @return if develop
     */
    public Boolean isDevelop() {
        return DEVELOPMENT.equals(scope);
    }

    /**
     * Test if current scope is a production environment or not
     *
     * @return
     */
    public Boolean isProduction() {
        return Config.get().getStringList(PRODUCTION_SCOPES, DEFAULT_PRODUCTION).contains(scope);
    }

}