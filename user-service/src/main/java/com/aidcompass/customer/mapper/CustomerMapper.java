package com.aidcompass.customer.mapper;

import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerEntity toEntity(UUID id, CustomerRegistrationDto customerRegistrationDto);

    PublicCustomerResponseDto toPublicResponseDto(CustomerEntity customerEntity);

    @Mapping(source = "customer.id", target = "id")
    @Mapping(source = "customer", target = "publicData")
    PrivateCustomerResponseDto toPrivateResponseDto(PublicCustomerResponseDto customer);

    void updateEntityFromDto(CustomerUpdateDto customerUpdateDto, @MappingTarget CustomerEntity customerEntity);
}
