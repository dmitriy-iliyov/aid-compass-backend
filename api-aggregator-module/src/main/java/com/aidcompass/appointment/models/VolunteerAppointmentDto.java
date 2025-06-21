package com.aidcompass.appointment.models;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VolunteerAppointmentDto(
        AppointmentResponseDto appointment,
        @JsonProperty("avatar_url")
        String avatarUrl,
        PublicCustomerResponseDto customer,
        List<PublicContactResponseDto> contacts
) { }
