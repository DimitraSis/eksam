package dk.routes;

import dk.controller.impl.DoctorControllerDB;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class DoctorsRouteDB {

    private final DoctorControllerDB dctrdb = new DoctorControllerDB();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/doctordb", () -> {
                post("/", dctrdb::create);
                get("/", dctrdb::readAll);
                get("/{id}", dctrdb::readById);
                put("/{id}", dctrdb::update);
                get("/speciality/{speciality}", dctrdb::doctorBySpeciality);
                get("/birthdate/range", dctrdb::doctorByBirthdateRange);
                delete("/{id}", dctrdb::delete);
            });
        };
    }


}
