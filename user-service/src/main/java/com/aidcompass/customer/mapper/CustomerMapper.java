package com.aidcompass.customer.mapper;

import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerEntity toEntity(UUID id, CustomerRegistrationDto dto);

    PublicCustomerResponseDto toPublicResponseDto(CustomerEntity entity);

    @Mapping(source = "profileStatus.status", target = "status")
    PrivateCustomerResponseDto toPrivateResponseDto(CustomerEntity entity);

    void updateEntityFromDto(CustomerUpdateDto dto, @MappingTarget CustomerEntity entity);
}
