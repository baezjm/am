package util.spark.config.route;

import com.google.inject.Singleton;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * Router implementing a simple health check endpoint.
 * <p>
 * Clients can verify the endpoint is alive by sending a GET request to ./ping.
 */
@Singleton
public class HealthCheckRouter implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {

        response.header("Content-Type", "text/plain");
        return "pong";
    }
}
