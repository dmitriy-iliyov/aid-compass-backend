package com.aidcompass.customer.mapper;

import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.PublicCustomerResponseDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "gender", expression = "java(com.aidcompass.gender.Gender.toEnum(dto.getGender()))")
    CustomerEntity toEntity(UUID id, CustomerDto dto);

    PublicCustomerResponseDto toPublicDto(CustomerEntity entity);

    List<PublicCustomerResponseDto> toPublicDtoList(List<CustomerEntity> entityList);

    @Mapping(source = "profileStatusEntity.profileStatus", target = "profileStatus")
    PrivateCustomerResponseDto toPrivateDto(CustomerEntity entity);

    @Mapping(target = "gender", expression = "java(com.aidcompass.gender.Gender.toEnum(dto.getGender()))")
    void updateEntityFromDto(CustomerDto dto, @MappingTarget CustomerEntity entity);
}
