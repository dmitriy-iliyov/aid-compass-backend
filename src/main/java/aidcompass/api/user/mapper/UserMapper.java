package aidcompass.api.user.mapper;

import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.general.utils.MapperUtils;
import aidcompass.api.user.models.dto.UserUpdateDto;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "role", constant = "ROLE_USER")
    UserEntity toEntity(UserRegistrationDto customerRegistrationDto);

    @Mapping(target = "createdAt", qualifiedByName = "formatCreatedDate", source = "createdAt")
    UserResponseDto toResponseDto(UserEntity customerEntity);

    List<UserResponseDto> toResponseDtoList(Iterable<UserEntity> customerEntities);

    UserUpdateDto toUpdateDto(UserRegistrationDto userRegistrationDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget UserEntity userEntity);
}
