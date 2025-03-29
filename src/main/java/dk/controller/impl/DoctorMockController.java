package dk.controller.impl;

import dk.Enum.Speciality;
import dk.controller.IController;
import dk.dao.impl.DoctorDaoMock;
import dk.dto.DoctorDto;
import dk.exception.Message;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;

public class DoctorMockController implements IController<DoctorDto,Integer, Speciality> {

    private DoctorDaoMock dcDaoMock;

    public DoctorMockController(){
        this.dcDaoMock = new DoctorDaoMock();
    }

    @Override
    public void readAll(Context ctx) {
        ctx.json(dcDaoMock.readAll());

    }

    @Override
    public void readById(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        DoctorDto doctor = dcDaoMock.readById(id);
        if(doctor != null){
            ctx.json(doctor);
        }else {
            ctx.status(404).json(new Message(404,"Doctor with this id not found, try again"));
        }
    }

    @Override
    public void create(Context ctx) {
        DoctorDto doctor = ctx.bodyAsClass(DoctorDto.class);

        if(doctor != null){
            try{
                dcDaoMock.create(doctor);
                ctx.status(201).json(doctor);
            }catch (Exception e){
                ctx.status(500).json(new Message(500,"Failed to create Doctor"));
            }
        }else {
            ctx.status(400).json(new Message(400,"Invalid Doctor data"));
        }

    }

    @Override
    public void update(Context ctx) {
        String id = ctx.pathParam("id");
        try{
            int idAsInt = Integer.parseInt(id);
            DoctorDto doctorDto = dcDaoMock.readById(idAsInt);
            if(doctorDto != null){
                DoctorDto doctorDtoUpdate = ctx.bodyAsClass(DoctorDto.class);
                ctx.status(200);
                dcDaoMock.update(idAsInt,doctorDtoUpdate);
                ctx.json(doctorDtoUpdate);
            }else {
                ctx.status(404);
                ctx.json(new Message(404,"Doctor with this id not found, try again"));
            }
        } catch (Exception e) {
            ctx.status(500).json(new Message(500,"Failed to update Doctor"));
        }

    }

    @Override
    public void delete(Context ctx) {
        String id = ctx.pathParam("id");
        try {
            int idAsInt = Integer.parseInt(id);
            DoctorDto doctorDto = dcDaoMock.readById(idAsInt);
            if (doctorDto != null) {
                dcDaoMock.delete(idAsInt);
                ctx.status(204).result("Doctor with id: " + id + " deleted");
            } else {
                ctx.status(404).result("Doctor with this id not found, try again");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID format");
        } catch (Exception e) {
            ctx.status(500).result("Internal server error occurred");
        }

    }

    @Override
    public void doctorBySpeciality(Context ctx) {
        String specialityStr = ctx.pathParam("speciality");
        try {

            Speciality speciality = Speciality.valueOf(specialityStr);
            ctx.json(dcDaoMock.doctorBySpeciality(speciality));

        } catch (Exception e) {
            ctx.status(400).result("Invalid speciality format");
        }

    }

    public void doctorByBirthdateRange(Context ctx) {

        String startDateStr = ctx.pathParam("startDate");
        String endDateStr = ctx.pathParam("endDate");
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            List<DoctorDto> doctorsInRange = dcDaoMock.doctorByBirthdateRange(startDate, endDate);
            ctx.json(doctorsInRange);

        }catch (NumberFormatException e){
            ctx.status(400).result("Invalid date format");

        } catch (Exception e) {
            ctx.status(500).result("server error occurred");
        }

    }
}
