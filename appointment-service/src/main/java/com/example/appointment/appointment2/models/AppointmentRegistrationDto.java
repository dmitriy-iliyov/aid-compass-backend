package com.example.appointment.models;

import com.example.appointment.user_appointment_date.UniqueUserAppointmentDate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@UniqueUserAppointmentDate
public class AppointmentRegistrationDto extends CustomBindingErrors {

    @NotNull(message = "Appointment date must not be null!")
    @Future(message = "Appointment date must be in the future!")
    private Instant appointmentDate;

    @NotBlank(message = "Topic shouldn't be empty or blank!")
    @Size(min = 10, max = 40, message = "Topic length must be greater than 10 and less than 40!")
    private String topic;

    @NotBlank(message = "Description shouldn't be empty or blank!")
    @Size(max = 80, message = "Description should less than 40!")
    private String description;

    @NotNull(message = "User id shouldn't be empty or blank!")
    @Positive(message = "Invalid value!")
    private UUID userId;

    @NotNull(message = "Volunteer id shouldn't be empty or blank!")
    @Positive(message = "Invalid value!")
    private UUID volunteerId;
}
