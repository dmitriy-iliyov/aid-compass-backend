package com.aidcompass.appointment.models;

import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.customer.dto.PublicCustomerResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VolunteerAppointmentDto(
        AppointmentResponseDto appointment,
        @JsonProperty("avatar_url")
        String avatarUrl,
        PublicCustomerResponseDto customer,
        List<PublicContactResponseDto> contacts
) { }
