package dk.dao.impl;

import dk.Enum.Speciality;
import dk.dao.IDao;
import dk.model.Doctor;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class DoctorDao implements IDao<Doctor,Integer, Speciality> {

    private static DoctorDao instance;
    private static EntityManagerFactory emf;

    public static DoctorDao getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            DoctorDao.emf = emf;
            instance = new DoctorDao();
        }
        return instance;
    }

    @Override
    public Doctor create(Doctor doctor) {
        try(var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(doctor);
            em.getTransaction().commit();
            return doctor;
        }
    }

    @Override
    public Doctor readById(Integer integer) {
       try(var em = emf.createEntityManager())
            {
                return em.find(Doctor.class, integer);
            }
    }

    @Override
    public List<Doctor> readAll() {
        try(var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);
            return query.getResultList();
        }
    }

    @Override
    public Doctor update(Integer integer, Doctor doctor) {
        try(var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Doctor doctor1 = em.find(Doctor.class, integer);

            doctor1.setName(doctor.getName());
            doctor1.setDateOfBirth(doctor.getDateOfBirth());
            doctor1.setYearOfGraduation(doctor.getYearOfGraduation());
            doctor1.setNameOfClinic(doctor.getNameOfClinic());
            doctor1.setSpeciality(doctor.getSpeciality());
            em.getTransaction().commit();
            return doctor1;
        }
    }

    @Override
    public void delete(Integer integer) {
        try(var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Doctor doctor = em.find(Doctor.class, integer);
            em.remove(doctor);
            em.getTransaction().commit();
        }

    }

    @Override
    public List<Doctor> doctorBySpeciality(Speciality speciality) {
        try(var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT d FROM Doctor d WHERE d.speciality = :speciality", Doctor.class);
            query.setParameter("speciality", speciality);
            return query.getResultList();
        }
    }


    public List<Doctor> doctorByBirthdateRange(LocalDate from, LocalDate to) {
        try(var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT d FROM Doctor d WHERE d.dateOfBirth BETWEEN :from AND :to", Doctor.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            return query.getResultList();
        }

    }
}
