package com.aidcompass.contact.controllers;

import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.services.ContactFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/contacts")
@RequiredArgsConstructor
public class AdminContactController {

    private final ContactFacade contactFacade;


    @PostMapping("/{uuid}")
    public ResponseEntity<PrivateContactResponseDto> createContact(@PathVariable("uuid") UUID ownerId,
                                                                   @RequestBody @Valid ContactCreateDto contact) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactFacade.save(ownerId, contact));
    }

    @PostMapping("/batch/{uuid}")
    public ResponseEntity<List<PrivateContactResponseDto>> createContacts(@PathVariable("uuid") UUID ownerId,
                                                                          @RequestBody
                                                                          @Valid ContactCreateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactFacade.saveAll(ownerId, wrappedContacts.contacts()));
    }

    @PatchMapping("/link-email/{id}/{uuid}")
    public ResponseEntity<?> linkEmailToAccount(@PathVariable("uuid") UUID ownerId,
                                                @PathVariable("id") Long id) {
        contactFacade.markEmailAsLinkedToAccount(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/private/{uuid}")
    public ResponseEntity<List<PrivateContactResponseDto>> getPrivateContacts(@PathVariable("uuid") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.findAllPrivateByOwnerId(ownerId));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<PrivateContactResponseDto> updateContact(@PathVariable("uuid") UUID ownerId,
                                                                   @RequestBody @Valid ContactUpdateDto contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.update(ownerId, contact));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<List<PrivateContactResponseDto>> updateAllContacts(@PathVariable("uuid") UUID ownerId,
                                                                             @RequestBody
                                                                             @Valid ContactUpdateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.updateAll(ownerId, wrappedContacts.contacts()));
    }

    @DeleteMapping("/{uuid}/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable("uuid") UUID ownerId,
                                           @PathVariable("id") Long id) {
        contactFacade.delete(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteAll(@PathVariable("uuid") UUID ownerId) {
        contactFacade.deleteAll(ownerId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
