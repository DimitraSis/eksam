package dk;

import dk.config.ApplicationConfig;
import dk.routes.Routes;
import io.javalin.Javalin;

public class Main {


    private static final Routes routes = new Routes();
    private static final Javalin app = Javalin.create();




    public static void main(String[] args) {



        app.updateConfig(ApplicationConfig::configuration);




        app.routes(routes.getRoutes(app));


        app.start(7071);



    }
}