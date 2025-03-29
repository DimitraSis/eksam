package dk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.Enum.Speciality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false,unique = true)
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private int yearOfGraduation;
    private String nameOfClinic;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
   private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JsonIgnore
    private Set<Appointment> appointment;

    public Doctor(String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
    }

    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointment.add(appointment);
            appointment.setDoctor(this);
        }
    }

    public void setAppointments(Set<Appointment> appointments) {
        if (appointments != null) {
            this.appointment = appointments;
            for (Appointment appointment : appointments) {
                appointment.setDoctor(this);
            }
        }
    }

   @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }




}
