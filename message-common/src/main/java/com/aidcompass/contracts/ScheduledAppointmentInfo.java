package com.aidcompass.contracts;

public record ScheduledAppointmentInfo(
        String customerFirstName,
        String customerSecondName,
        String customerLastName,
        String customerContact,
        String volunteerType,
        String volunteerFirstName,
        String volunteerSecondName,
        String volunteerLastName,
        String volunteerContact,
        String appointmentType,
        String appointmentDate,
        String appointmentDescription
) { }
