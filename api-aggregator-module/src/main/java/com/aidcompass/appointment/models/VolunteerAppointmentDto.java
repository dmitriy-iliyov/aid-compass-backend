package com.aidcompass.appointment.models;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;

import java.util.List;

public record VolunteerAppointmentDto(
        AppointmentResponseDto appointment,
        PublicCustomerResponseDto customer,
        List<PublicContactResponseDto> contacts
) { }
