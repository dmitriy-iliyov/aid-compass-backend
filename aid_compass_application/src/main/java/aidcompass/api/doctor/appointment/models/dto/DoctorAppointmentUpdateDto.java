package aidcompass.api.doctor.appointment.models.dto;

import aidcompass.api.doctor.appointment.validation.doctor_appointment_update.ValidDoctorAppointmentUpdate;
import aidcompass.api.general.models.CustomBindingErrors;
import aidcompass.api.general.models.appointment.AppointmentUpdateDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidDoctorAppointmentUpdate
public class DoctorAppointmentUpdateDto extends CustomBindingErrors implements AppointmentUpdateDto {

    @NotNull(message = "Appointment id shouldn't be null!")
    @Min(value = 0, message = "Invalid value!")
    private Long id;

    @NotNull(message = "Appointment date must not be null!")
    @Future(message = "Appointment date must be in the future!")
    private Instant appointmentDate;

    @NotBlank(message = "Topic shouldn't be empty or blank!")
    @Size(min = 10, max = 40, message = "Topic length must be greater than 10 and less than 40!")
    private String topic;

    @NotBlank(message = "Description shouldn't be empty or blank!")
    @Size(max = 80)
    private String description;

    @NotNull(message = "User id shouldn't be empty or blank!")
    @Min(value = 0, message = "Invalid value!")
    private Long userId;

    @NotNull(message = "Doctor id shouldn't be empty or blank!")
    @Min(value = 0, message = "Invalid value!")
    private Long volunteerId;

}
