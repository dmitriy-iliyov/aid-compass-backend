package com.example.user.mapper;

import com.example.authority.mapper.AuthorityMapper;
import com.example.authority.models.Authority;
import com.example.authority.models.AuthorityEntity;
import com.example.user.models.dto.SystemUserDto;
import com.example.user.models.dto.SystemUserUpdateDto;
import com.example.user.models.dto.UserRegistrationDto;
import com.example.user.models.dto.UserResponseDto;
import com.example.user.models.dto.UserUpdateDto;
import com.example.user.models.entity.UnconfirmedUserEntity;
import com.example.user.models.entity.UserEntity;
import com.example.utils.MapperUtils;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-18T18:01:02+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private MapperUtils mapperUtils;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        UnconfirmedUserEntity unconfirmedUserEntity = new UnconfirmedUserEntity();

        unconfirmedUserEntity.setPassword( mapperUtils.encodePassword( userRegistrationDto.password() ) );
        unconfirmedUserEntity.setEmail( userRegistrationDto.email() );

        return unconfirmedUserEntity;
    }

    @Override
    public SystemUserDto toSystemDto(UnconfirmedUserEntity unconfirmedUserEntity) {
        if ( unconfirmedUserEntity == null ) {
            return null;
        }

        UUID id = null;
        String email = null;
        String password = null;

        id = unconfirmedUserEntity.getId();
        email = unconfirmedUserEntity.getEmail();
        password = unconfirmedUserEntity.getPassword();

        List<Authority> authorities = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        SystemUserDto systemUserDto = new SystemUserDto( id, email, password, authorities, createdAt, updatedAt );

        return systemUserDto;
    }

    @Override
    public SystemUserDto toSystemDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        List<Authority> authorities = null;
        UUID id = null;
        String email = null;
        String password = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        authorities = authorityMapper.toAuthorityList( userEntity.getAuthorities() );
        id = userEntity.getId();
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        createdAt = userEntity.getCreatedAt();
        updatedAt = userEntity.getUpdatedAt();

        SystemUserDto systemUserDto = new SystemUserDto( id, email, password, authorities, createdAt, updatedAt );

        return systemUserDto;
    }

    @Override
    public UserEntity toEntity(SystemUserDto systemUserDto) {
        if ( systemUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( systemUserDto.id() );
        userEntity.setEmail( systemUserDto.email() );
        userEntity.setPassword( systemUserDto.password() );
        userEntity.setCreatedAt( systemUserDto.createdAt() );
        userEntity.setUpdatedAt( systemUserDto.updatedAt() );

        return userEntity;
    }

    @Override
    public UserResponseDto toResponseDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        Instant createdAt = null;
        UUID id = null;
        String email = null;

        if ( userEntity.getCreatedAt() != null ) {
            createdAt = Instant.parse( mapperUtils.formatDate( userEntity.getCreatedAt() ) );
        }
        id = userEntity.getId();
        email = userEntity.getEmail();

        UserResponseDto userResponseDto = new UserResponseDto( id, email, createdAt );

        return userResponseDto;
    }

    @Override
    public SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto userUpdateDto) {
        if ( userUpdateDto == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = userUpdateDto.email();
        password = userUpdateDto.password();

        UUID id = null;
        List<Authority> authorities = null;

        SystemUserUpdateDto systemUserUpdateDto = new SystemUserUpdateDto( id, email, password, authorities );

        return systemUserUpdateDto;
    }

    @Override
    public void updateEntityFromDto(SystemUserUpdateDto systemUserUpdateDto, UserEntity userEntity) {
        if ( systemUserUpdateDto == null ) {
            return;
        }

        if ( systemUserUpdateDto.getPassword() != null ) {
            userEntity.setPassword( mapperUtils.encodePassword( systemUserUpdateDto.getPassword() ) );
        }
        if ( systemUserUpdateDto.getEmail() != null ) {
            userEntity.setEmail( systemUserUpdateDto.getEmail() );
        }
        if ( userEntity.getAuthorities() != null ) {
            List<AuthorityEntity> list = authorityListToAuthorityEntityList( systemUserUpdateDto.getAuthorities() );
            if ( list != null ) {
                userEntity.getAuthorities().clear();
                userEntity.getAuthorities().addAll( list );
            }
        }
        else {
            List<AuthorityEntity> list = authorityListToAuthorityEntityList( systemUserUpdateDto.getAuthorities() );
            if ( list != null ) {
                userEntity.setAuthorities( list );
            }
        }
    }

    protected AuthorityEntity authorityToAuthorityEntity(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityEntity authorityEntity = new AuthorityEntity();

        authorityEntity.setAuthority( authority );

        return authorityEntity;
    }

    protected List<AuthorityEntity> authorityListToAuthorityEntityList(List<Authority> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorityEntity> list1 = new ArrayList<AuthorityEntity>( list.size() );
        for ( Authority authority : list ) {
            list1.add( authorityToAuthorityEntity( authority ) );
        }

        return list1;
    }
}
