package dk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.Enum.Speciality;
import dk.model.Doctor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private int yearOfGraduation;
    private String nameOfClinic;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    public DoctorDto(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.yearOfGraduation = doctor.getYearOfGraduation();
        this.nameOfClinic = doctor.getNameOfClinic();
        this.speciality = doctor.getSpeciality();
    }
}
