package com.aidcompass.contact.controllers;

import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.facades.GeneralFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/{owner_id}/contacts")
@RequiredArgsConstructor
public class AdminContactController {

    private final GeneralFacade generalFacade;


    @PostMapping
    public ResponseEntity<PrivateContactResponseDto> createContact(@PathVariable("owner_id") UUID ownerId,
                                                                   @RequestBody @Valid ContactCreateDto contact) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalFacade.save(ownerId, contact));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PrivateContactResponseDto>> createContacts(@PathVariable("owner_id") UUID ownerId,
                                                                          @RequestBody
                                                                          @Valid ContactCreateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalFacade.saveAll(ownerId, wrappedContacts.contacts()));
    }

    @PatchMapping("/{contact_id}/link-email")
    public ResponseEntity<?> linkEmailToAccount(@PathVariable("owner_id") UUID ownerId,
                                                @PathVariable("contact_id") Long id) {
        generalFacade.markEmailAsLinkedToAccount(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/private")
    public ResponseEntity<List<PrivateContactResponseDto>> getPrivateContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalFacade.findAllPrivateByOwnerId(ownerId));
    }

    @PatchMapping
    public ResponseEntity<PrivateContactResponseDto> updateContact(@PathVariable("owner_id") UUID ownerId,
                                                                   @RequestBody @Valid ContactUpdateDto contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalFacade.update(ownerId, contact));
    }

    @PutMapping
    public ResponseEntity<List<PrivateContactResponseDto>> updateAllContacts(@PathVariable("owner_id") UUID ownerId,
                                                                             @RequestBody
                                                                             @Valid ContactUpdateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalFacade.updateAll(ownerId, wrappedContacts.contacts()));
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<?> deleteContact(@PathVariable("owner_id") UUID ownerId,
                                           @PathVariable("contact_id") Long contactId) {
        generalFacade.delete(ownerId, contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(@PathVariable("owner_id") UUID ownerId) {
        generalFacade.deleteAll(ownerId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
