package dk.config;

import dk.routes.Routes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig {



    public static void configuration(JavalinConfig config) {
        config.routing.contextPath = "/api"; // base path for all routes
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.plugins.register(new RouteOverviewPlugin("/")); // enables route overview at /


    }

    /**
     * Start server and stop server is used for testing purposes
     */
    public static void startServer(Javalin app, int port) {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        app.routes(routes.getRoutes(app));
        app.start(port);
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }
}
