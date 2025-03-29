package dk.dao.impl;

import dk.Enum.Speciality;
import dk.dao.IDao;
import dk.dto.DoctorDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoMock implements IDao<DoctorDto, Integer, Speciality>{

    private Integer idCounter = 1;

    List<DoctorDto>doctorList;
    {
        doctorList=new ArrayList<>(List.of(
           new DoctorDto(idCounter++,"Dr Alice Smith", LocalDate.of(1975,4,12),
                   2000,"City Health Clinic", Speciality.FAMILY_MEDICINE),
                new DoctorDto(idCounter++,"Dr Bob Johnson", LocalDate.of(1980,8,5),
                        2005,"Downtown Medical", Speciality.SURGERY),
                new DoctorDto(idCounter++,"Dr Clara Lee", LocalDate.of(1983,7,22),
                        2008,"Green Valley", Speciality.PEDIATRICS)
                ));
    }

    @Override
    public DoctorDto create(DoctorDto doctorDto) {
        Integer id = idCounter++;
        doctorDto.setId(id);
        doctorList.add(doctorDto);
        return doctorDto;
    }

    @Override
    public DoctorDto readById(Integer integer) {
        for(DoctorDto doctorDto: doctorList){
            if(doctorDto.getId()==integer){
                return doctorDto;
            }
        }
        return null;
    }

    @Override
    public List<DoctorDto> readAll() {
        return doctorList;
    }

    @Override
    public DoctorDto update(Integer integer, DoctorDto doctorDto) {
        for(DoctorDto doctorDto1: doctorList){
            if(doctorDto1.getId()==integer){
                doctorDto1.setName(doctorDto.getName());
                doctorDto1.setDateOfBirth(doctorDto.getDateOfBirth());
                doctorDto1.setYearOfGraduation(doctorDto.getYearOfGraduation());
                doctorDto1.setNameOfClinic(doctorDto.getNameOfClinic());
                doctorDto1.setSpeciality(doctorDto.getSpeciality());
                return doctorDto1;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer integer) {
        for(DoctorDto doctorDto: doctorList){
            if(doctorDto.getId()==integer){
                doctorList.remove(doctorDto);
                return;
            }
        }

    }

    @Override
    public List<DoctorDto> doctorBySpeciality(Speciality speciality) {

        return doctorList.stream().filter(doctorDto -> doctorDto.getSpeciality().equals(speciality)).toList();
    }

    public List<DoctorDto> doctorByBirthdateRange(LocalDate from, LocalDate to) {

        return doctorList.stream().filter(doctorDto -> doctorDto.getDateOfBirth().isAfter(from) && doctorDto.getDateOfBirth().isBefore(to)).toList();
    }
}
