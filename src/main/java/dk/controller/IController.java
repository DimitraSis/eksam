package dk.controller;



import io.javalin.http.Context;

public interface IController<T, D, E> {

    void readAll(Context ctx);
    void readById(Context ctx) ;
    void create(Context ctx);
    void update(Context ctx);
    void delete(Context ctx);
    void doctorBySpeciality(Context ctx);


}
