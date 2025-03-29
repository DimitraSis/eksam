package dk.routes;

import dk.controller.impl.DoctorMockController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class DoctorsRoute {

    private final DoctorMockController dctr = new DoctorMockController();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/doctor", () -> {
                post("/", dctr::create);
                get("/", dctr::readAll);
                get("/{id}", dctr::readById);
                put("/{id}", dctr::update);
                get("/speciality/{speciality}", dctr::doctorBySpeciality);
                get("/birthdate/range", dctr::doctorByBirthdateRange);
                delete("/{id}", dctr::delete);
            });
        };
    }
}
