package aidcompass.api.doctor.appointment.validation.doctor_appointment_update;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorAppointmentUpdateValidator.class)
@Documented
public @interface ValidDoctorAppointmentUpdate {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
