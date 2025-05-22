package com.aidcompass.contact.facades;

import com.aidcompass.client.AuthService;
import com.aidcompass.client.ConfirmationService;
import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.models.dto.markers.CreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.services.ContactService;
import com.aidcompass.contact.validation.validators.PermissionValidator;
import com.aidcompass.contact.validation.validators.CountValidator;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.exceptions.invalid_input.InvalidAttemptMarkAsLinkedException;
import com.aidcompass.exceptions.invalid_input.BaseInvalidContactDeleteException;
import com.aidcompass.global_exceptions.UserNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import com.aidcompass.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.exceptions.invalid_input.NotEnoughSpaseForNewContactExceptionBase;
import com.aidcompass.client.models.ConfirmationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class GeneralFacadeImpl implements GeneralFacade {

    private final ContactService contactService;
    private final PermissionValidator permissionValidator;
    private final CountValidator countValidator;

    private final ConfirmationService confirmationService;
    private final AuthService authService;


    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto contact) {
        boolean isUserExists = authService.existsById(ownerId);
        if (isUserExists) {
            if (countValidator.hasSpaceForContact(ownerId, contact)) {
                PrivateContactResponseDto dto = contactService.save(ownerId, contact);
                //send confirmation message to confirm resource
                confirmationService.sendConfirmationRequest(
                        new ConfirmationRequestDto(dto.id(), dto.contact(), ContactType.valueOf(dto.type())));
                return dto;
            }
            throw new NotEnoughSpaseForNewContactExceptionBase(
                    List.of(new ErrorDto("contact", "Impossible to add new " + contact.type().toString() + "!"))
            );
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contacts) {
        boolean isUserExists = authService.existsById(ownerId);
        List<CreateDto> createDtoList = new ArrayList<>(contacts);
        if (isUserExists && countValidator.hasSpaceForContacts(ownerId, createDtoList)) {
            List<PrivateContactResponseDto> dtoList = contactService.saveAll(ownerId, contacts);
            for(PrivateContactResponseDto dto: dtoList) {
                //send confirmation message to confirm resource
                confirmationService.sendConfirmationRequest(
                        new ConfirmationRequestDto(dto.id(), dto.contact(), ContactType.valueOf(dto.type())));
            }
            return dtoList;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void requestConfirmation(UUID ownerId, Long contactId) {
        SystemContactDto systemDto = permissionValidator.isConfirmPermit(ownerId, contactId);
        confirmationService.sendConfirmationRequest(
                new ConfirmationRequestDto(systemDto.id(), systemDto.contact(), systemDto.type())
        );
    }

    @Override
    public void markEmailAsLinkedToAccount(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = permissionValidator.isLinkingPermit(ownerId, contactId);
        if (!errors.isEmpty()) {
            throw new InvalidAttemptMarkAsLinkedException(errors);
        }
        contactService.markContactAsLinked(ownerId, contactId);
        //обновить линкованый имейл в auth service перевыпустить токен аутентификации(перелогин в аккаунт)
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
            throw new BaseInvalidContactUpdateException(errors);
        }
        //send confirmation message to confirm resource if resource is changed
        return contactService.update(ownerId, contact,
                contactDto -> confirmationService.sendConfirmationRequest(
                        new ConfirmationRequestDto(contactDto.id(), contactDto.contact(), contactDto.type())));
    }

    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contacts) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(ownerId, contacts);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactUpdateException(errors);
        }
        //send confirmation message to confirm resource if resource is changed
        return contactService.updateAll(ownerId, contacts,
                contact -> confirmationService.sendConfirmationRequest(
                        new ConfirmationRequestDto(contact.id(), contact.contact(), contact.type())));
    }

    @Override
    public void delete(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = permissionValidator.isDeletePermit(ownerId, contactId);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactDeleteException(errors);
        }
        contactService.deleteById(ownerId, contactId);
    }

    @Override
    public void deleteAll(UUID ownerId) {
        contactService.deleteAll(ownerId);
    }
}