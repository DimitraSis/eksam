package dk.controller.impl;

import dk.Enum.Speciality;
import dk.config.HibernateConfig;
import dk.controller.IController;
import dk.dao.impl.DoctorDao;
import dk.dto.DoctorDto;
import dk.exception.Message;
import dk.model.Doctor;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class DoctorControllerDB implements IController<Doctor,Integer, Speciality> {

    private DoctorDao dao;

public DoctorControllerDB() {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = DoctorDao.getInstance(emf);
    }

    @Override
    public void readAll(Context ctx) {
    ctx.json(dao.readAll());

    }

    @Override
    public void readById(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Doctor doctor = dao.readById(id);
        if (doctor != null) {
            ctx.json(doctor);
        } else {
            ctx.status(404).json(new Message(404,"Doctor with this id not found, try again"));
        }

    }

    @Override
    public void create(Context ctx) {
        Doctor request = ctx.bodyAsClass(Doctor.class);
        if (request != null) {
            try {
                Doctor doctor = dao.create(request);
                DoctorDto doc = new DoctorDto(doctor);
                ctx.status(201).json(doc); // Respond with the created entity
            } catch (Exception e) {
                ctx.status(500).json(new Message(500, "Failed to create Doctor")); // Handle unexpected errors
            }
        } else {
            ctx.status(400).json(new Message(400, "Invalid Doctor data")); // Handle invalid data
        }

    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // entity
        Doctor updateDoc = dao.update(id, ctx.bodyAsClass(Doctor.class));
        // dto
        DoctorDto dcDto = new DoctorDto(updateDoc);
        // response
        ctx.res().setStatus(200);
        ctx.json(dcDto, DoctorDto.class);

    }

    @Override
    public void delete(Context ctx) {
        String id = ctx.pathParam("id");
        try {
            int idAsInt = Integer.parseInt(id);
            Doctor doctor = dao.readById(idAsInt);
            if (doctor != null) {
                dao.delete(idAsInt);
                ctx.status(204).result("Doctor with id: " + id + " deleted");
            } else {
                ctx.status(404).result("Doctor with this id not found, try again");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID format");
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Failed to delete Doctor"));
        }

    }

    @Override
    public void doctorBySpeciality(Context ctx) {
        String speciality = ctx.pathParam("speciality");
        try{
            Speciality specialityAsEnum = Speciality.valueOf(speciality);
            ctx.json(dao.doctorBySpeciality(specialityAsEnum));
        }catch (Exception e){
            ctx.status(500).json(new Message(500,"Failed to get Doctor by speciality"));
        }

    }

    public void doctorByBirthdateRange(Context ctx){
        String startDateStr = ctx.queryParam("from");
        String endDateStr = ctx.queryParam("to");
        try{
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            List<Doctor> doctorsInRange = dao.doctorByBirthdateRange(startDate,endDate);
            ctx.json(doctorsInRange);
        }catch (NumberFormatException e){
            ctx.status(400).result("Invalid date format");
        }catch (Exception e){
            ctx.status(500).result("server error occurred");
        }

    }


}
