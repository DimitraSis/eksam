package dk.dao.impl;

import dk.Enum.Speciality;
import dk.config.HibernateConfig;
import dk.model.Appointment;
import dk.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorDaoTest {

    private static EntityManagerFactory emfTest;
    private static EntityManager em;

    private static DoctorDao dDao;
    private static Doctor d1, d2, d3, d4;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();

        dDao = new DoctorDao();
        DoctorDao.getInstance(emfTest);
    }

    @BeforeEach
    void setUp() {
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Appointment d").executeUpdate();
            em.createQuery("DELETE FROM Doctor a").executeUpdate();
            // Reset sequence
            em.createNativeQuery("ALTER SEQUENCE doctor_doctor_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE appointment_appointment_id_seq RESTART WITH 1").executeUpdate();
            // Insert test data
             d1 = new Doctor("Dr Alice Smith", LocalDate.of(1975, 4, 12),
                    2000, "City Health Clinic", Speciality.FAMILY_MEDICINE);
            Doctor d2 = new Doctor("Dr Bob Johnson", LocalDate.of(1980, 8, 5),
                    2005, "Downtown Medical", Speciality.SURGERY);
            Doctor d3 = new Doctor("Dr Clara Lee", LocalDate.of(1983, 7, 22),
                    2008, "Green Valley", Speciality.PEDIATRICS);
            Doctor d4 = new Doctor("Dr David Park", LocalDate.of(1978, 11, 15),
                    2003, "Hillside Health Practice", Speciality.PSYCHIATRY);

            Appointment a1 = new Appointment("John Smith", LocalDate.of(2023, 11, 24),
                    LocalTime.of(9, 45), "First visit");
            Appointment a2 = new Appointment("alice Johnson", LocalDate.of(2023, 11, 27),
                    LocalTime.of(10, 30), "Follow up");


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

    }

    @Test
    void create() {
        Doctor d3 = new Doctor("Dr Clara Lee", LocalDate.of(1983, 7, 22),
                2008, "Green Valley", Speciality.PEDIATRICS);
        dDao.create(d3);
        assertEquals(3, dDao.readAll().size());

    }

    @Test
    void readById() {
        assertEquals(1, dDao.readById(1).getId());
    }

    @Test
    void readAll() {
        assertEquals(2, dDao.readAll().size());
    }

    @Test
    void update() {
        d1.setYearOfGraduation(2010);
        dDao.update(d1.getId(), d1);
        assertEquals(2010, dDao.readById(d1.getId()).getYearOfGraduation());
    }

    @Test
    void delete() {
        dDao.delete(1);
        assertEquals(1, dDao.readAll().size());
    }
}