package com.aidcompass;

import com.aidcompass.contracts.ContactReadService;
import com.aidcompass.customer.CustomerReadService;
import com.aidcompass.customer.dto.PublicCustomerResponseDto;
import com.aidcompass.doctor.contracts.DoctorReadService;
import com.aidcompass.doctor.dto.PublicDoctorResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;
import com.aidcompass.dto.UserDto;
import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.enums.ContactType;
import com.aidcompass.exception.UnsupportedUserTypeException;
import com.aidcompass.jurist.contracts.JuristReadService;
import com.aidcompass.jurist.dto.PublicJuristResponseDto;
import com.aidcompass.models.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAggregatorImpl implements UserAggregator {

    private final CustomerReadService customerReadService;
    private final DoctorReadService doctorReadService;
    private final JuristReadService juristReadService;
    private final ContactReadService contactReadService;


    @Override
    public UserDto findUserByIdAndType(UUID id, UserType type) {
        PublicContactResponseDto contactDto = findPrimaryContactByOwnerId(id);
        if (contactDto != null) {
            switch (type){
                case CUSTOMER -> {
                    PublicCustomerResponseDto dto = customerReadService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case DOCTOR -> {
                    PublicDoctorResponseDto dto = doctorReadService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                case JURIST -> {
                    PublicJuristResponseDto dto = juristReadService.findPublicById(id);
                    return new UserDto(dto.id(), dto.firstName(), dto.secondName(), dto.lastName(), contactDto.type(), contactDto.contact());
                }
                default -> throw new UnsupportedUserTypeException();
            }
        }
        return null;
    }

    @Override
    public Map<UUID, UserDto> findAllCustomerByIdIn(Set<UUID> ids) {
        List<PublicCustomerResponseDto> customerList = customerReadService.findAllByIds(ids);
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
        return contactReadService.findPrimaryByOwnerId(id)
                .stream()
                .filter(dto -> dto.type().equals(ContactType.EMAIL))
                .findFirst().orElse(null);
    }

    private Map<UUID, SystemContactDto> findPrimaryContactByOwnerIdIn(Set<UUID> ids) {
        List<SystemContactDto> dtoList = contactReadService.findAllPrimaryByOwnerIdIn(ids);
        return dtoList.stream().collect(Collectors.toMap(SystemContactDto::getOwnerId, Function.identity()));
    }
}
