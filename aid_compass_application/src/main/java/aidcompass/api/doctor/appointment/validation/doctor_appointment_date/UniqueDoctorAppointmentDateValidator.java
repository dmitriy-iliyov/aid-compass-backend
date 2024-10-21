package aidcompass.api.doctor.appointment.validation.doctor_appointment_date;

import aidcompass.api.doctor.appointment.DoctorAppointmentServices;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentResponseDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

public class UniqueDoctorAppointmentDateValidator implements ConstraintValidator<UniqueDoctorAppointmentDate, DoctorAppointmentRegistrationDto> {

    @Autowired
    private DoctorAppointmentServices doctorAppointmentServices;

    @Override
    public boolean isValid(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto,
                           ConstraintValidatorContext constraintValidatorContext) throws IllegalArgumentException{

        if (doctorAppointmentServices == null )
            return true;

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (doctorAppointmentRegistrationDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Appointment not passed!")
                    .addPropertyNode("appointment")
                    .addConstraintViolation();
            return false;
        }

        Long doctorId = doctorAppointmentRegistrationDto.getDoctorId();
        Instant appointmentDate = doctorAppointmentRegistrationDto.getAppointmentDate();
        List<DoctorAppointmentResponseDto> doctorsAppointments = doctorAppointmentServices.findAllByDoctorId(doctorId);

        if(doctorsAppointments.isEmpty())
            return true;

        for(DoctorAppointmentResponseDto doctorAppointment : doctorsAppointments){
            if (doctorAppointment.getAppointmentDate().equals(appointmentDate)){
                constraintValidatorContext.buildConstraintViolationWithTemplate("Doctor have an appointment at this time!")
                        .addPropertyNode("appointmentDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
