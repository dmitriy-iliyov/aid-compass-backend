package aidcompass.api.user.validation.user_appointment_date;

import aidcompass.api.general.models.appointment.AppointmentRegistrationDto;
import aidcompass.api.general.models.appointment.AppointmentResponseDto;
import aidcompass.api.user.appointment.UserAppointmentService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

public class UniqueUserAppointmentDateValidator implements ConstraintValidator<UniqueUserAppointmentDate, AppointmentRegistrationDto> {

    @Autowired
    private UserAppointmentService userAppointmentService;

    @Override
    public boolean isValid(AppointmentRegistrationDto appointmentRegistrationDto,
                           ConstraintValidatorContext constraintValidatorContext) throws IllegalArgumentException{

        if (userAppointmentService == null) {
            throw new IllegalStateException("UserRepository is not properly initialized.");
        }

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (appointmentRegistrationDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Appointment not passed!")
                    .addPropertyNode("appointment")
                    .addConstraintViolation();
            return false;
        }

        Long userId = appointmentRegistrationDto.getUserId();
        Instant appointmentDate = appointmentRegistrationDto.getAppointmentDate();
        List<AppointmentResponseDto> userAppointments = userAppointmentService.getAllUserAppointments(userId);

        if(userAppointments.isEmpty())
            return true;

        for (AppointmentResponseDto userAppointment : userAppointments){
            System.out.println(userAppointment);
            if (userAppointment.getAppointmentDate().equals(appointmentDate)){

                Class<?> c = userAppointment.getClass();
                System.out.println(c);

                constraintValidatorContext.buildConstraintViolationWithTemplate("You have an appointment at this time!")
                        .addPropertyNode("appointmentDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
