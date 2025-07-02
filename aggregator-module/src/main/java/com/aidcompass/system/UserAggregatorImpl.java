package com.aidcompass.system;

import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.services.ContactService;
import com.aidcompass.customer.models.PublicCustomerResponseDto;
import com.aidcompass.customer.services.CustomerService;
import com.aidcompass.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.services.DoctorService;
import com.aidcompass.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.services.JuristService;
import com.aidcompass.system.exception.UnsupportedUserTypeException;
import com.aidcompass.information.dto.UserDto;
import com.aidcompass.system.models.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAggregatorImpl implements UserAggregator {

    private final CustomerService customerService;
    private final DoctorService doctorService;
    private final JuristService juristService;
    private final ContactService contactService;


    @Override
    public UserDto findUserByIdAndType(UUID id, UserType type) {
        PublicContactResponseDto contactDto = findPrimaryContactByOwnerId(id);
        if (contactDto != null) {
            switch (type){
                case CUSTOMER -> {
                    PublicCustomerResponseDto dto = customerService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case DOCTOR -> {
                    PublicDoctorResponseDto dto = doctorService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case JURIST -> {
                    PublicJuristResponseDto dto = juristService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                default -> throw new UnsupportedUserTypeException();
            }
        }
        return null;
    }

    @Override
    public Map<UUID, UserDto> findAllCustomerByIdIn(Set<UUID> ids) {
        List<PublicCustomerResponseDto> customerList = customerService.findAllByIds(ids);
        Map<UUID, SystemContactDto> contactMap = findPrimaryContactByOwnerIdIn(ids);
        return customerList.stream()
                .collect(
                        Collectors.toMap(
                            PublicCustomerResponseDto::id,
                            dto -> {
                                SystemContactDto contact = contactMap.get(dto.id());
                                return new UserDto(
                                        dto.id(),
                                        dto.firstName(),
                                        dto.secondName(),
                                        dto.lastName(),
                                        contact.getType(),
                                        contact.getContact()
                                );
                            })
                );
    }

    @Override
    public PublicContactResponseDto findPrimaryContactByOwnerId(UUID id) {
        return contactService.findPrimaryByOwnerId(id)
                .stream()
                .filter(dto -> dto.type().equals(ContactType.EMAIL))
                .findFirst().orElse(null);
    }

    private Map<UUID, SystemContactDto> findPrimaryContactByOwnerIdIn(Set<UUID> ids) {
        List<SystemContactDto> dtoList = contactService.findAllPrimaryByOwnerIdIn(ids);
        return dtoList.stream().collect(Collectors.toMap(SystemContactDto::getOwnerId, Function.identity()));
    }
}
