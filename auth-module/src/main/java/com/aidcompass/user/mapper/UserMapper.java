package com.aidcompass.user.mapper;


import com.aidcompass.security.core.models.authority.models.AuthorityMapper;
import com.aidcompass.base_dto.*;
import com.aidcompass.user.models.SystemUserUpdateDto;
import com.aidcompass.user.models.UserEntity;
import com.aidcompass.user.models.UnconfirmedUserEntity;
import org.mapstruct.*;

import java.util.UUID;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class, AuthorityMapper.class})
public interface UserMapper {

    @Deprecated
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "dto.password")
    UnconfirmedUserEntity toUnconfirmedEntity(UUID id, UserRegistrationDto dto);

    SystemUserDto toSystemDto(Long emailId, UnconfirmedUserEntity entity);

    SystemUserDto toSystemDto(UnconfirmedUserEntity entity);

    @Mapping(target = "authorities", qualifiedByName = "toAuthorityList", source = "authorities")
    SystemUserDto toSystemDto(UserEntity entity);

    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(SystemUserDto systemUserDto);

    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
    UserResponseDto toResponseDto(UserEntity entity);

    SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    void updateEntityFromDto(SystemUserUpdateDto dto, @MappingTarget UserEntity entity);
}
