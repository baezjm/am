package util.spark.config;

import util.java.config.config.Config;
import util.java.config.config.RuntimeEnvironment;
import util.spark.config.route.ErrorHandlerRouter;
import util.spark.config.route.HealthCheckRouter;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DeploymentContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import spark.RouteGroup;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static spark.Spark.*;

/**
 * Application that initialize configuration and dependency injection with Guice.
 * <p>
 * <p>
 */
public abstract class Application extends AbstractModule implements SparkApplication, RouteGroup {

    /**
     * Overloadable Configurations
     */
    public static final String SERVER_PORT = "server.port";
    public static final String SERVER_ADDRESS = "server.address";
    public static final String SERVER_BASE_THREADS = "server.base.threads";
    public static final String SERVER_MAX_CORE_MULTIPLIER = "server.max.core.multiplier";
    public static final String SERVER_MIN_CORE_MULTIPLIER = "server.min.core.multiplier";
    public static final String SERVER_REQUEST_TIMEOUT = "server.request.timeout";
    public static final String APPLICATION_PATH = "application.path";

    /**
     * MDC key to identify a request
     */
    public static final String REQUEST_ID = "request_id";

    protected final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Injector name to add to Config.addInjector()
     */
    public static final String APP = "globalconfig/spark.util.java.util.config.config.app";


    private int port;
    private String address;
    private int baseThreads;
    private int maxMultiplier;
    private int minMultiplier;
    private int timeout;
    private String basePath;


    /**
     * Create an application with default configuration.
     */
    public Application() {
        try {
            setUpConfigurationManagement();
            initializeWithDefaults();
            dependencyInjectionConfiguration();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initiate the application with the configuration and dependency injection module loaded.
     */
    @Override
    public void init() {
        configureServer();
        setUpRoutes();
        awaitInitialization();
    }

    /**
     * Destroy the application
     */
    @Override
    public void destroy() {
        stop();
        Config.clearInjectors();
    }

    /**
     * Create the module and add the injector to the globalconfig.java.util.config.config module.
     */
    protected void dependencyInjectionConfiguration() {
        Config.addInjector(APP, Guice.createInjector(this));
    }


    /**
     * Configure the embedded jetty server to start.
     */
    protected void configureServer() {
        final int processors = Runtime.getRuntime().availableProcessors();

        final int maxThreads = processors * maxMultiplier + baseThreads;
        final int minThreads = processors * minMultiplier + baseThreads;

        port(port);
        ipAddress(address);

        threadPool(maxThreads, minThreads, timeout);

        log.info("Listening in{}:{} using thread pool: [min:{} | max:{} | timeout:{}]", address, port, minThreads, maxThreads, timeout);
    }

    /**
     * Load the route groups.
     */
    private void setUpRoutes() {

        get("/ping", getInstance(HealthCheckRouter.class));

        before((request, response) -> putRequestId());
        after((request, response) -> MDC.clear());

        path(basePath, this);

        getInstance(ErrorHandlerRouter.class).register();
    }

    /**
     * Configure the context variables of the cascaded properties.
     *
     * @environment="dev" or "prod"
     * @stack="scope"
     */
    protected void setUpConfigurationManagement() {
        try {
            DeploymentContext context = ConfigurationManager.getDeploymentContext();
            context.setDeploymentEnvironment(RuntimeEnvironment.get().isDevelop() ? "dev" : "prod");
            context.setDeploymentStack(RuntimeEnvironment.get().getScope());
            ConfigurationManager.loadCascadedPropertiesFromResources("config");

        } catch (IOException e) {
            log.error("Error setting up configuration management", e);
        }
    }

    /**
     * @return the Class of this application.
     */
    public static <T> T getInstance(Class<T> clazz) {
        return Config.safeGetInstance(APP, clazz);
    }

    /**
     * @return the Class of this application.
     */
    public static <T> T getInstance(Key<T> key) {
        return Config.safeGetInstance(APP, key);
    }

    /**
     * Set the request id in the MDC.
     */
    private static void putRequestId() {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
    }

    /**
     * @return the request id previously set in the MDC.
     */
    public static String getRequestId() {
        return MDC.get(REQUEST_ID);
    }

    /**
     * Port application.
     *
     * @param port
     * @return
     */
    public Application withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Address to use to bind the process.
     *
     * @param address
     * @return
     */
    public Application withAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Base threads to start the application.
     *
     * @param baseThreads
     * @return
     */
    public Application withBaseThreads(int baseThreads) {
        this.baseThreads = baseThreads;
        return this;
    }

    /**
     * Multiplier to use with the cores machine to calculate the max thread.
     *
     * @param maxMultiplier
     * @return
     */
    public Application withMaxMultiplier(int maxMultiplier) {
        this.maxMultiplier = maxMultiplier;
        return this;
    }

    /**
     * Multiplier to use with the cores machine to calculate the min thread.
     *
     * @param minMultiplier
     * @return
     */
    public Application withMinMultiplier(int minMultiplier) {
        this.minMultiplier = minMultiplier;
        return this;
    }

    /**
     * Max time to respond the client.
     *
     * @param timeout
     * @return
     */
    public Application withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    /**
     * The base path to load all the routes.
     *
     * @param basePath
     * @return
     */
    public Application withBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    /**
     * Initialize application configuration fields with defaults.
     *
     * @throws UnknownHostException
     */
    protected void initializeWithDefaults() throws UnknownHostException {
        port = Config.get().getInt(SERVER_PORT, 8080);
        final String ipAddress = InetAddress.getLocalHost().getHostAddress();
        address = Config.get().getString(SERVER_ADDRESS, ipAddress);

        baseThreads = Config.get().getInt(SERVER_BASE_THREADS, 3);
        maxMultiplier = Config.get().getInt(SERVER_MAX_CORE_MULTIPLIER, 2);
        minMultiplier = Config.get().getInt(SERVER_MIN_CORE_MULTIPLIER, 1);
        timeout = Config.get().getInt(SERVER_REQUEST_TIMEOUT, 25000);
        basePath = Config.get().getString(APPLICATION_PATH, "/");
    }

}