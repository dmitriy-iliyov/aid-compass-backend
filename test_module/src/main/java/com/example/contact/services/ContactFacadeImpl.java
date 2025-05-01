package com.example.contact.services;

import com.example.client.AuthService;
import com.example.client.ConfirmationService;
import com.example.contact.models.dto.ContactCreateDto;
import com.example.contact.models.dto.ContactUpdateDto;
import com.example.contact.models.dto.PrivateContactResponseDto;
import com.example.contact.models.dto.PublicContactResponseDto;
import com.example.contact.validation.ContactPermissionValidator;
import com.example.contact.validation.count.ContactCountValidator;
import com.example.exceptions.invalid_input.InvalidAttemptMarkAsLinkedException;
import com.example.exceptions.invalid_input.InvalidContactDeleteException;
import com.example.global_exceptions.UserNotFoundException;
import com.example.global_exceptions.dto.ErrorDto;
import com.example.exceptions.invalid_input.InvalidContactUpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class ContactFacadeImpl implements ContactFacade {

    private final ContactService contactService;
    private final ContactPermissionValidator permissionValidator;
    private final ContactCountValidator countValidator;
    private final ConfirmationService confirmationService;
    private final AuthService authService;


    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto contact) {
        boolean isUserExists = authService.existsById(ownerId);
        if (isUserExists && countValidator.hasSpaceForContact(ownerId, contact)) {
            PrivateContactResponseDto privateContactResponseDto = contactService.save(ownerId, contact);
//            confirmationService.sendConfirmationMessage(contact.contact());
            return privateContactResponseDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contacts) {
        boolean isUserExists = authService.existsById(ownerId);
        if (isUserExists && countValidator.hasSpaceForContacts(ownerId, contacts)) {
            return contactService.saveAll(ownerId, contacts);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void markEmailAsLinkedToAccount(UUID ownerId, Long id) {
        List<ErrorDto> errors = permissionValidator.isLinkingPermit(ownerId, id);
        if (!errors.isEmpty()) {
            throw new InvalidAttemptMarkAsLinkedException(errors);
        }
        contactService.markContactAsLinked(ownerId, id);
    }

    @Override
    public List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId) {
        return contactService.findAllPrivateByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        return contactService.findAllPublicByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId) {
        return contactService.findPrimaryByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId) {
        return contactService.findSecondaryByOwnerId(ownerId);
    }

    @Override
    public PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto contact) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(ownerId, contact);
        if (!errors.isEmpty()) {
            throw new InvalidContactUpdateException(errors);
        }
        return contactService.updateById(contact);
    }

    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contacts) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(ownerId, contacts);
        if (!errors.isEmpty()) {
            throw new InvalidContactUpdateException(errors);
        }
        return contactService.updateAll(ownerId, contacts);
    }

    @Override
    public void delete(UUID ownerId, Long id) {
        List<ErrorDto> errors = permissionValidator.isDeletePermit(ownerId, id);
        if (!errors.isEmpty()) {
            throw new InvalidContactDeleteException(errors);
        }
        contactService.deleteById(id);
    }

    @Override
    public void deleteAll(UUID ownerId) {
        contactService.deleteAll(ownerId);
    }
}