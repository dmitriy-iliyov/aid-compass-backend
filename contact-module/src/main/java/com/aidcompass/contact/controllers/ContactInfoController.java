package com.aidcompass.contact.controllers;

import com.aidcompass.contact.mappers.ContactMapper;
import com.aidcompass.contact.repositories.ContactRepository;
import com.aidcompass.enums.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts/info")
@RequiredArgsConstructor
public class ContactInfoController {

    final ContactRepository repository;
    final ContactMapper mapper;


    @GetMapping("/enums")
    public ResponseEntity<?> getAllEnums() {
        Map<String, String> enums = Map.of((ContactType.class).getName(), Arrays.toString(ContactType.values()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enums);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllContacts() {
        return ResponseEntity.ok(mapper.toSystemDtoList(repository.findAll()));
    }
}
