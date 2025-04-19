package com.example.customer.models.dto;

import com.example.contacts.models.ContactsDto;
import com.example.rest_clients.user.dto.UserUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerUpdateDto(
        UserUpdateDto user,

        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "First name should contain only Ukrainian")
        String firstName,

        @NotBlank(message = "Second name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]+$", message = "Second name should contain only Ukrainian")
        String secondName,

        @Valid
        ContactsDto contacts
) { }
