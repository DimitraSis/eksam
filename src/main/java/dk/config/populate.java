package dk.config;

import dk.Enum.Speciality;
import dk.dao.impl.DoctorDao;
import dk.dto.DoctorDto;
import dk.model.Appointment;
import dk.model.Doctor;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        DoctorDao dao = DoctorDao.getInstance(emf);


        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

           Doctor d1= new Doctor("Dr Alice Smith", LocalDate.of(1975,4,12),
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
}
