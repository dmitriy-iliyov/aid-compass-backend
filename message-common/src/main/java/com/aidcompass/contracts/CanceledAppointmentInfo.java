package com.aidcompass.contracts;

public record CanceledAppointmentInfo(
        String customerFirstName,
        String customerSecondName,
        String customerLastName,
        String customerContact,
        String volunteerFirstName,
        String volunteerSecondName,
        String volunteerLastName,
        String volunteerContact,
        String appointmentDate,
        String appointmentType
) { }
