package aidcompass.api.doctor.appointment.models;


import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.general.models.appointment.AppointmentBaseEntity;
import aidcompass.api.user.models.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "doctor_appointments")
public class DoctorAppointmentEntity extends AppointmentBaseEntity {

    public DoctorAppointmentEntity(Instant appointmentDate, String topic, String description, UserEntity user,
                                   DoctorEntity doctor){
        this.setAppointmentDate(appointmentDate);
        this.setTopic(topic);
        this.setDescription(description);
        this.setUser(user);
        this.setVolunteer(doctor);
        this.setCreatedAt(Instant.now());
    }
}
