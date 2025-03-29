package dk.controller.impl;

import dk.Enum.Speciality;
import dk.config.ApplicationConfig;
import dk.config.HibernateConfig;
import dk.model.Appointment;
import dk.model.Doctor;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class DoctorControllerDBTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/doctordb";
    private static DoctorControllerDB dCtrlDB;
    private static EntityManagerFactory emfTest;
    private static Doctor d1, d2, d3, d4;

    @BeforeAll
    static void beforeAll()
    {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        dCtrlDB = new DoctorControllerDB();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp()
    {


        try (var em = emfTest.createEntityManager())
        {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Appointment d").executeUpdate();
            em.createQuery("DELETE FROM Doctor a").executeUpdate();
            // Reset sequence
            em.createNativeQuery("ALTER SEQUENCE doctor_doctor_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE appointment_appointment_id_seq RESTART WITH 1").executeUpdate();
            // Insert test data
            d1= new Doctor("Dr Alice Smith", LocalDate.of(1975,4,12),
                    2000,"City Health Clinic", Speciality.FAMILY_MEDICINE);
            Doctor d2 = new Doctor("Dr Bob Johnson", LocalDate.of(1980,8,5),
                    2005,"Downtown Medical", Speciality.SURGERY);
            Doctor d3 = new Doctor("Dr Clara Lee", LocalDate.of(1983,7,22),
                    2008,"Green Valley", Speciality.PEDIATRICS);
            Doctor d4 = new Doctor("Dr David Park", LocalDate.of(1978,11,15),
                    2003,"Hillside Health Practice", Speciality.PSYCHIATRY);

            Appointment a1= new Appointment("John Smith", LocalDate.of(2023,11,24),
                    LocalTime.of(9,45),"First visit");
            Appointment a2= new Appointment("alice Johnson", LocalDate.of(2023,11,27),
                    LocalTime.of(10,30),"Follow up");


            d1.setAppointments(Set.of(a1));
            d2.setAppointments(Set.of(a2));

            em.persist(d1);
            em.persist(d2);


            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown()
    {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    @Test
    void readAll() {
        given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void readById() {
        given()
                .when()
                .get(BASE_URL + "/" + 1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(1));
    }

    @Test
    void create() {
Doctor d3 = new Doctor("Dr Clara Lee", LocalDate.of(1983,7,22),
                2008,"Green Valley", Speciality.PEDIATRICS);
        given()
                .contentType("application/json")
                .body(d3)
                .when()
                .post(BASE_URL+"/")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().body().jsonPath().getInt("id");


    }

    @Test
    void update() {
        Doctor toUpdate= new Doctor("Dr Alice Smith", LocalDate.of(1975,4,12),
                2010,"City Health Clinic", Speciality.FAMILY_MEDICINE);
        given()
                .contentType("application/json")
                .body(toUpdate)
                .when()
                .put(BASE_URL+"/" + d1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("yearOfGraduation", equalTo(2010));
    }

    @Test
    void delete() {
        given()
                .when()
                .delete(BASE_URL + "/" + d1.getId())
                .then()
                .assertThat()
                .statusCode(204);
    }

    @Test
    void doctorBySpeciality() {
        given()
                .when()
                .get(BASE_URL + "/speciality/FAMILY_MEDICINE")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void doctorByBirthdateRange() {
        given()
                .when()
                .get(BASE_URL + "/birthdate/range?from=1970-01-01&to=1980-01-01")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(1));
    }
}