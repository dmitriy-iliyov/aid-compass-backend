package aidcompass.api.doctor.appointment;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.general.models.VolunteerBaseEntity;
import aidcompass.api.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointmentEntity, Long> {

    List<DoctorAppointmentEntity> findAllByVolunteer(VolunteerBaseEntity volunteerEntity);

    List<DoctorAppointmentEntity> findAllByUser(UserEntity userEntity);

}