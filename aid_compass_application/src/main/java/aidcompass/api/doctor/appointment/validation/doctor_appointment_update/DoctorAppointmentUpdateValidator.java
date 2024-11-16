package aidcompass.api.doctor.appointment.validation.doctor_appointment_update;

import aidcompass.api.doctor.appointment.DoctorAppointmentServices;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.general.models.appointment.AppointmentResponseDto;
import aidcompass.api.user.appointment.UserAppointmentService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class DoctorAppointmentUpdateValidator implements ConstraintValidator<ValidDoctorAppointmentUpdate, DoctorAppointmentUpdateDto> {

    @Autowired
    private DoctorAppointmentServices doctorAppointmentServices;

    @Autowired
    private UserAppointmentService userAppointmentService;

    @Override
    public boolean isValid(DoctorAppointmentUpdateDto doctorAppointmentUpdateDto, ConstraintValidatorContext constraintValidatorContext) {

        if (doctorAppointmentServices == null){
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (doctorAppointmentUpdateDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Appointment not passed!")
                    .addPropertyNode("appointment")
                    .addConstraintViolation();
            return false;
        }

        Long existingDoctorAppointmentId = doctorAppointmentUpdateDto.getId();
        Instant existingAppointmentDate = doctorAppointmentUpdateDto.getAppointmentDate();

        Long userId = doctorAppointmentUpdateDto.getUserId();
        Long doctorId = doctorAppointmentUpdateDto.getVolunteerId();

        List<DoctorAppointmentResponseDto> doctorAppointments = doctorAppointmentServices.findAllByDoctorId(doctorId);
        List<AppointmentResponseDto> userAppointments = userAppointmentService.getAllUserAppointments(userId);
        for(DoctorAppointmentResponseDto doctorAppointment : doctorAppointments){
            if (!Objects.equals(doctorAppointment.getId(), existingDoctorAppointmentId) &&
                    doctorAppointment.getAppointmentDate().equals(existingAppointmentDate)){
                constraintValidatorContext.buildConstraintViolationWithTemplate("Doctor have an appointment at this time!")
                        .addPropertyNode("appointmentDate")
                        .addConstraintViolation();
                return false;
            }
        }
        for(AppointmentResponseDto userAppointment : userAppointments){
            if (!Objects.equals(userAppointment.getId(), existingDoctorAppointmentId) &&
                    userAppointment.getAppointmentDate().equals(existingAppointmentDate)){
                constraintValidatorContext.buildConstraintViolationWithTemplate("You have an appointment at this time!")
                        .addPropertyNode("appointmentDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
