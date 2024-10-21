package aidcompass.api.user.mapper;

import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.general.utils.MapperUtils;
import aidcompass.api.user.models.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MapperUtils.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    UserEntity toEntity(UserRegistrationDto customerRegistrationDto);

    @Mapping(target = "createdAt", qualifiedByName = "formatCreatedDate", source = "createdAt")
    UserResponseDto toResponseDto(UserEntity customerEntity);

    List<UserResponseDto> toResponseDtoList(Iterable<UserEntity> customerEntities);

    UserUpdateDto toUpdateDto(UserRegistrationDto userRegistrationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    void updateEntityFromUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget UserEntity userEntity);
}
