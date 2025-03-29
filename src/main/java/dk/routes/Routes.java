package dk.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {



    private final DoctorsRoute dctrRoute = new DoctorsRoute();
    private final DoctorsRouteDB dctrRouteDB = new DoctorsRouteDB();


    private void requestInfoHandler(io.javalin.http.Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
       ctx.attribute("request-info", requestInfo); // Add request info to the
    }


    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.routes(() -> {

                app.before(this::requestInfoHandler);
                app.before(ctx -> {
                    ctx.header("Access-Control-Allow-Origin", "*");
                    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
                    ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
                    ctx.header("Access-Control-Allow-Credentials", "true");

                    if(ctx.method().equals("OPTIONS")) {
                        ctx.status(200).result("OK");
                    }
                });


                path("/",dctrRoute.getRoutes());
                path("/",dctrRouteDB.getRoutes());

            });
        };
    }
}
