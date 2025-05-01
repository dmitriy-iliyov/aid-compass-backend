package com.example.customer.mapper;

import com.example.contact.mapper.ContactsMapper;
import com.example.customer.models.CustomerEntity;
import com.example.customer.models.dto.CustomerRegistrationDto;
import com.example.customer.models.dto.CustomerUpdateDto;
import com.example.customer.models.dto.PrivateCustomerResponseDto;
import com.example.customer.models.dto.PublicCustomerResponseDto;
import com.example.clients.user.dto.UserResponseDto;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ContactsMapper.class})
public interface CustomerMapper {

    @Mapping(source = "contacts", target = "contacts", qualifiedByName = "toEntity")
    CustomerEntity toEntity(UUID id, CustomerRegistrationDto customerRegistrationDto);

    @Mapping(source = "contacts", target = "contacts", qualifiedByName = "toDto")
    PublicCustomerResponseDto toPublicResponseDto(CustomerEntity customerEntity);

    @Mapping(source = "customer.id", target = "id")
    @Mapping(source = "customer", target = "publicData")
    @Mapping(source = "user", target = "privateData")
    PrivateCustomerResponseDto toPrivateResponseDto(PublicCustomerResponseDto customer, UserResponseDto user);

    @Mapping(source = "contacts", target = "contacts", qualifiedByName = "toEntity")
    void updateEntityFromDto(CustomerUpdateDto customerUpdateDto, @MappingTarget CustomerEntity customerEntity);
}
