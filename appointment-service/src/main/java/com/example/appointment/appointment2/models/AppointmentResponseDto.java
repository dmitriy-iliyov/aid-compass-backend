package com.example.appointment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private Instant appointmentDate;
    private String topic;
    private String description;
    private Long userId;
    private Long volunteerId;
}
