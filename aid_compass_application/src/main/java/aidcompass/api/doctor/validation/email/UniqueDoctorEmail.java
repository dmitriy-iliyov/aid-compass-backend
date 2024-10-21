package aidcompass.api.doctor.validation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDoctorEmailValidator.class)
@Documented
public @interface UniqueDoctorEmail {
    String message() default "Email isn't unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
