package util.spark.config;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.netflix.config.ConfigurationManager;
import org.junit.After;
import org.junit.Test;
import util.java.config.config.Config;
import util.java.config.exception.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static spark.Spark.get;

/**
 * Created by abertolo on 15/08/17.
 */
public class ApplicationTest {

    @After
    public void tearDown() throws Exception {
        Config.clearInjectors();
    }

    @Test
    public void init() throws Exception {
        final Application application = new Application() {

            @Override
            public void addRoutes() {
                get("/jrr", (request, response) -> "crack");

                get("/fail", (request, response) -> {
                    throw new RuntimeException("Ups!");
                });


                get("/ise", (request, response) -> {
                    throw new InternalServerErrorException("Ups!");
                });


                get("/nf", (request, response) -> {
                    throw new NotFoundException("nf not found!");
                });

                get("/br", (request, response) -> {
                    throw new BadRequestException("very bad request!");
                });

                get("/c", (request, response) -> {
                    throw new ConflictException("conflict request!");
                });

                get("/f", (request, response) -> {
                    throw new ForbiddenException("Halt there!");
                });

                get("/na", (request, response) -> {
                    throw new NotAcceptableException("no no nooooooooo!");
                });


                get("/u", (request, response) -> {
                    throw new UnauthorizedException("login pls!");
                });
            }

            @Override
            protected void configure() {

            }
        }.withAddress("localhost")
                .withPort(8879)
                .withBasePath("path")
                .withBaseThreads(3)
                .withMaxMultiplier(2)
                .withMinMultiplier(1)
                .withTimeout(1000);

        application.init();

        assertEquals("pong", getResponse("http://localhost:8879/ping"));
        assertEquals("crack", getResponse("http://localhost:8879/path/jrr"));

        assertNotFound();
        assertGenericException();

        assertInternalServerErrorException();
        assertNotFoundException();
        assertBadRequestException();
        assertForbiddenException();
        assertNotAcceptableException();

        application.destroy();

        try {
            Thread.sleep(1000);
            getResponse("http://localhost:8879/ping");
            fail();
        } catch (ConnectException e) {
            assertEquals("Connection refused", e.getMessage());
        }
    }

    private void assertBadRequestException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/br");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 400 for URL: http://localhost:8879/path/br", e.getMessage());
        }
    }

    private void assertConflictException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/c");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 409 for URL: http://localhost:8879/path/c", e.getMessage());
        }
    }

    private void assertForbiddenException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/f");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 403 for URL: http://localhost:8879/path/f", e.getMessage());
        }
    }

    private void assertNotAcceptableException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/na");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 406 for URL: http://localhost:8879/path/na", e.getMessage());
        }
    }

    private  void assertUnauthorizedException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/u");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 401 for URL: http://localhost:8879/path/u", e.getMessage());
        }
    }

    private void assertInternalServerErrorException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/ise");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 500 for URL: http://localhost:8879/path/ise", e.getMessage());
        }
    }

    private void assertNotFoundException() throws IOException {
        try {
            getResponse("http://localhost:8879/path/nf");
            fail();
        } catch (FileNotFoundException e) {
            assertEquals("http://localhost:8879/path/nf", e.getMessage());
        }
    }


    private void assertNotFound() throws IOException {
        try {
            getResponse("http://localhost:8879/endpoint_not_found");
            fail();
        } catch (FileNotFoundException e) {
            assertEquals("http://localhost:8879/endpoint_not_found", e.getMessage());
        }
    }

    private void assertGenericException() {
        try {
            getResponse("http://localhost:8879/path/fail");
            fail();
        } catch (IOException e) {
            assertEquals("Server returned HTTP response code: 500 for URL: http://localhost:8879/path/fail", e.getMessage());
        }
    }


    @Test
    public void dependencyInjectionConfiguration() throws Exception {
        final Application application = new Application() {

            @Override
            public void addRoutes() {

            }

            @Override
            protected void configure() {
                bind(String.class).toInstance("JRR");
                bind(String.class).annotatedWith(Names.named("test1")).toInstance("JRR1");
                bind(String.class).annotatedWith(Names.named("test2")).toInstance("JRR2");
            }
        };

        assertEquals("JRR", application.getInstance(String.class));
        assertEquals("JRR1", application.getInstance(Key.get(String.class, Names.named("test1"))));
        assertEquals("JRR2", application.getInstance(Key.get(String.class, Names.named("test2"))));
    }

    @Test
    public void setUpConfigurationManagement() throws Exception {
        final Application application = new Application() {

            @Override
            public void addRoutes() {

            }

            @Override
            protected void configure() {
                bind(String.class).toInstance("JRR");
            }
        };

        assertEquals("dev", ConfigurationManager.getDeploymentContext().getDeploymentEnvironment());
        assertEquals("dev", ConfigurationManager.getDeploymentContext().getDeploymentStack());
    }

    private String getResponse(String url) throws IOException {
        final URLConnection connection = new URL(url).openConnection();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            stringBuilder.append(inputLine);
        in.close();
        return stringBuilder.toString();
    }
}