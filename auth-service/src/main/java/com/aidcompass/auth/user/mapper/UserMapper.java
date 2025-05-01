package com.aidcompass.auth.user.mapper;


import com.aidcompass.auth.authority.mapper.AuthorityMapper;
import com.aidcompass.auth.user.models.dto.*;
import com.aidcompass.auth.user.models.entity.UserEntity;
import com.example.user.models.entity.UnconfirmedUserEntity;
import com.aidcompass.auth.utils.MapperUtils;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class, AuthorityMapper.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationDto userRegistrationDto);

    SystemUserDto toSystemDto(UnconfirmedUserEntity unconfirmedUserEntity);

    @Mapping(target = "authorities", qualifiedByName = "toAuthorityList", source = "authorities")
    SystemUserDto toSystemDto(UserEntity userEntity);

    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(SystemUserDto systemUserDto);

    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
    UserResponseDto toResponseDto(UserEntity userEntity);

    SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto userUpdateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    void updateEntityFromDto(SystemUserUpdateDto systemUserUpdateDto, @MappingTarget UserEntity userEntity);
}
