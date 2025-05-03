package com.aidcompass.contact.validation;

import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.contact.models.dto.SystemContactDto;
import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.contact.ContactValidator;
import com.aidcompass.contact.validation.ownership.OwnershipValidator;
import com.aidcompass.exceptions.invalid_input.DoubleContactIdException;
import com.aidcompass.exceptions.invalid_input.InvalidAttemptChangeToPrimaryException;
import com.aidcompass.exceptions.invalid_input.OwnerShipException;
import com.aidcompass.exceptions.not_found.ContactNotFoundByIdException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// необходимо оптимизировать запросы в бд вытягиваеться вся сущность которая часто нужна толбко для проверки
// принадлежности к пользователю возможно как то по другому
@RequiredArgsConstructor
public class ContactPermissionValidatorImpl implements ContactPermissionValidator {

    private final SystemContactService service;
    private final ContactValidator contactValidator;
    private final OwnershipValidator ownershipValidator;


    /**
     * Validates a list of contact updates to ensure no permitted changes are made.
     *
     * @param ownerId ID of the contact owner
     * @param contactUpdateDtoList List of contact update DTOs
     * @return List of errors found during validation
     * @throws DoubleContactIdException if duplicate contact IDs are found in the list
     * @throws OwnerShipException if one of passed contacts isn't owners
     * @throws InvalidAttemptChangeToPrimaryException if an attempt is made to set an unconfirmed contact as primary
     */
    @Override
    public List<ErrorDto> isUpdatePermit(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList) {
        List<ErrorDto> errors = new ArrayList<>();
        Set<Long> contactIds = contactUpdateDtoList.stream()
                .map(ContactUpdateDto::id)
                .collect(Collectors.toSet());

        // check for duplicate contacts id
        if (contactIds.size() != contactUpdateDtoList.size()) {
            throw new DoubleContactIdException();
        }

        List<SystemContactDto> systemContactDtoList = service.findAllByOwnerId(ownerId);

        // check ownership
        ownershipValidator.assertOwnership(contactIds.stream().toList(), systemContactDtoList);

        Map<Long, SystemContactDto> mapOfIdDto = systemContactDtoList.stream()
                .collect(Collectors.toMap(
                        SystemContactDto::id,
                        Function.identity())
                );

        for (ContactUpdateDto contactDto: contactUpdateDtoList) {
            SystemContactDto systemContactDto = mapOfIdDto.get(contactDto.id());
            // check if a confirmed contact is being set as primary
            if (!systemContactDto.isConfirmed() && contactDto.isPrimary()) {
                throw new InvalidAttemptChangeToPrimaryException();
            }
            // check uniqueness of the contact
            contactValidator.checkUniquesForType(ownerId, contactDto, errors);
        }

        return errors;
    }

    /**
     * Validates a  contact update to ensure no permitted changes are made.
     *
     * @param ownerId ID of the contact owner
     * @param contactUpdateDto contact update DTO
     * @return List of errors found during validation
     * @throws OwnerShipException if passed contact isn't owners
     * @throws InvalidAttemptChangeToPrimaryException if an attempt is made to set an unconfirmed contact as primary
     */
    @Override
    public List<ErrorDto> isUpdatePermit(UUID ownerId, ContactUpdateDto contactUpdateDto) {
        List<ErrorDto> errors = new ArrayList<>();
        List<SystemContactDto> systemContactDtoList = service.findAllByOwnerId(ownerId);

        ownershipValidator.assertOwnership(contactUpdateDto.id(), systemContactDtoList);

        Map<Long, SystemContactDto> mapOfIdDto = systemContactDtoList.stream()
                .collect(Collectors.toMap(SystemContactDto::id, Function.identity()));
        if (!mapOfIdDto.get(contactUpdateDto.id()).isConfirmed() && contactUpdateDto.isPrimary()) {
            throw new InvalidAttemptChangeToPrimaryException();
        }

        contactValidator.checkUniquesForType(ownerId, contactUpdateDto, errors);

        return errors;
    }

    @Override
    public List<ErrorDto> isDeletePermit(UUID ownerId, Long id) {
        List<ErrorDto> errors = new ArrayList<>();

        // вот тут запрос на получаение списка сущностей из бд тольок для чека на права
        List<SystemContactDto> systemContactDtoList = service.findAllByOwnerId(ownerId);
        ownershipValidator.assertOwnership(id, systemContactDtoList);

        SystemContactDto contact = systemContactDtoList.stream()
                .filter(systemContactDto -> systemContactDto.id().equals(id))
                .findFirst()
                .orElseThrow(ContactNotFoundByIdException::new);

        if (contact.isLinkedToAccount()) {
            errors.add(new ErrorDto("contact", "This contact is linked to account, that's why it can't be deleted!"));
        }

        return errors;
    }

    @Override
    public List<ErrorDto> isLinkingPermit(UUID ownerId, Long id) {
        List<ErrorDto> errors = new ArrayList<>();

        List<SystemContactDto> systemContactDtoList = service.findAllByOwnerId(ownerId);
        ownershipValidator.assertOwnership(id, systemContactDtoList);

        SystemContactDto systemContactDto = systemContactDtoList.stream()
                .filter(dto -> dto.id().equals(id))
                .findFirst()
                .orElseThrow(ContactNotFoundByIdException::new);

        if (!contactValidator.isEmailValid(systemContactDto.contact())) {
            errors.add(new ErrorDto("contact", "Only email can be linked to account!"));
        }

        if (!systemContactDto.isConfirmed()) {
            errors.add(new ErrorDto("contact", "Unconfirmed email can't be linked to account!"));
        }

        return errors;
    }
}
