package com.aidcompass.contact.controllers;

import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.services.ContactFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactFacade contactFacade;


    @PostMapping("/{owner_id}")
    public ResponseEntity<PrivateContactResponseDto> createContact(@PathVariable("owner_id") UUID ownerId,
                                                                   @RequestBody @Valid ContactCreateDto contact) {
        System.out.println();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactFacade.save(ownerId, contact));
    }

    @PostMapping("/batch/{owner_id}")
    public ResponseEntity<List<PrivateContactResponseDto>> createContacts(@PathVariable("owner_id") UUID ownerId,
                                                                          @RequestBody
                                                                          @Valid ContactCreateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactFacade.saveAll(ownerId, wrappedContacts.contacts()));
    }

    @PostMapping("/{contact_id}/confirmation-request/{owner_id}")
    public ResponseEntity<?> requestConfirmation(@PathVariable("contact_id") Long contactId,
                                                 @PathVariable("owner_id")UUID ownerId) {
        contactFacade.requestConfirmation(ownerId, contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{owner_id}/{contact_id}/link-email")
    public ResponseEntity<?> linkEmailToAccount(@PathVariable("owner_id") UUID ownerId,
                                                @PathVariable("contact_id") Long id) {
        contactFacade.markEmailAsLinkedToAccount(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/primary/{owner_id}")
    public ResponseEntity<List<PublicContactResponseDto>> getPrimaryContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.findPrimaryByOwnerId(ownerId));
    }

    @GetMapping("/secondary/{owner_id}")
    public ResponseEntity<List<PublicContactResponseDto>> getSecondaryContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.findSecondaryByOwnerId(ownerId));
    }

    @GetMapping("/private/{owner_id}")
    public ResponseEntity<List<PrivateContactResponseDto>> getPrivateContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.findAllPrivateByOwnerId(ownerId));
    }

    @GetMapping("/public/{owner_id}")
    public ResponseEntity<List<PublicContactResponseDto>> getPublicContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.findAllPublicByOwnerId(ownerId));
    }

    @PatchMapping("/{owner_id}")
    public ResponseEntity<PrivateContactResponseDto> updateContact(@PathVariable("owner_id") UUID ownerId,
                                                                   @RequestBody @Valid ContactUpdateDto contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.update(ownerId, contact));
    }

    @PutMapping("/{owner_id}")
    public ResponseEntity<List<PrivateContactResponseDto>> updateAllContacts(@PathVariable("owner_id") UUID ownerId,
                                                                             @RequestBody
                                                                             @Valid ContactUpdateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactFacade.updateAll(ownerId, wrappedContacts.contacts()));
    }

    @DeleteMapping("/{owner_id}/{contact_id}")
    public ResponseEntity<?> deleteContact(@PathVariable("owner_id") UUID ownerId,
                                           @PathVariable("contact_id") Long id) {
        contactFacade.delete(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{owner_id}")
    public ResponseEntity<?> deleteAll(@PathVariable("owner_id") UUID ownerId) {
        contactFacade.deleteAll(ownerId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
