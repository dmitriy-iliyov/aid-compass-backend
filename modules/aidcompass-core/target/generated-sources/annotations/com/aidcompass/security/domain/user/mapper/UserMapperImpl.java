package com.aidcompass.security.domain.user.mapper;

import com.aidcompass.security.domain.authority.models.Authority;
import com.aidcompass.security.domain.authority.models.AuthorityEntity;
import com.aidcompass.security.domain.authority.models.AuthorityMapper;
import com.aidcompass.security.domain.user.models.UnconfirmedUserEntity;
import com.aidcompass.security.domain.user.models.UserEntity;
import com.aidcompass.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.security.domain.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.security.domain.user.models.dto.UserRegistrationDto;
import com.aidcompass.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.security.domain.user.models.dto.UserUpdateDto;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:32+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private MapperUtils mapperUtils;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationDto dto) {
        if ( dto == null ) {
            return null;
        }

        UnconfirmedUserEntity unconfirmedUserEntity = new UnconfirmedUserEntity();

        unconfirmedUserEntity.setPassword( mapperUtils.encodePassword( dto.password() ) );
        unconfirmedUserEntity.setEmail( dto.email() );

        return unconfirmedUserEntity;
    }

    @Override
    public UnconfirmedUserEntity toUnconfirmedEntity(UUID id, UserRegistrationDto dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        UnconfirmedUserEntity unconfirmedUserEntity = new UnconfirmedUserEntity();

        if ( dto != null ) {
            unconfirmedUserEntity.setPassword( mapperUtils.encodePassword( dto.password() ) );
            unconfirmedUserEntity.setEmail( dto.email() );
        }
        unconfirmedUserEntity.setId( id );

        return unconfirmedUserEntity;
    }

    @Override
    public SystemUserDto toSystemDto(Long emailId, UnconfirmedUserEntity entity) {
        if ( emailId == null && entity == null ) {
            return null;
        }

        UUID id = null;
        String email = null;
        String password = null;
        if ( entity != null ) {
            id = entity.getId();
            email = entity.getEmail();
            password = entity.getPassword();
        }
        Long emailId1 = null;
        emailId1 = emailId;

        List<Authority> authorities = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        SystemUserDto systemUserDto = new SystemUserDto( id, emailId1, email, password, authorities, createdAt, updatedAt );

        return systemUserDto;
    }

    @Override
    public SystemUserDto toSystemDto(UnconfirmedUserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String email = null;
        String password = null;

        id = entity.getId();
        email = entity.getEmail();
        password = entity.getPassword();

        Long emailId = null;
        List<Authority> authorities = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        SystemUserDto systemUserDto = new SystemUserDto( id, emailId, email, password, authorities, createdAt, updatedAt );

        return systemUserDto;
    }

    @Override
    public SystemUserDto toSystemDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<Authority> authorities = null;
        UUID id = null;
        Long emailId = null;
        String email = null;
        String password = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        authorities = authorityMapper.toAuthorityList( entity.getAuthorities() );
        id = entity.getId();
        emailId = entity.getEmailId();
        email = entity.getEmail();
        password = entity.getPassword();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();

        SystemUserDto systemUserDto = new SystemUserDto( id, emailId, email, password, authorities, createdAt, updatedAt );

        return systemUserDto;
    }

    @Override
    public UserEntity toEntity(SystemUserDto systemUserDto) {
        if ( systemUserDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( systemUserDto.id() );
        userEntity.setEmailId( systemUserDto.emailId() );
        userEntity.setEmail( systemUserDto.email() );
        userEntity.setPassword( systemUserDto.password() );
        userEntity.setCreatedAt( systemUserDto.createdAt() );
        userEntity.setUpdatedAt( systemUserDto.updatedAt() );

        return userEntity;
    }

    @Override
    public UserResponseDto toResponseDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String createdAt = null;
        UUID id = null;
        String email = null;

        createdAt = mapperUtils.formatDate( entity.getCreatedAt() );
        id = entity.getId();
        email = entity.getEmail();

        UserResponseDto userResponseDto = new UserResponseDto( id, email, createdAt );

        return userResponseDto;
    }

    @Override
    public SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto dto) {
        if ( dto == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = dto.email();
        password = dto.password();

        UUID id = null;
        List<Authority> authorities = null;

        SystemUserUpdateDto systemUserUpdateDto = new SystemUserUpdateDto( id, email, password, authorities );

        return systemUserUpdateDto;
    }

    @Override
    public void updateEntityFromDto(SystemUserUpdateDto dto, UserEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( mapperUtils.encodePassword( dto.getPassword() ) );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( entity.getAuthorities() != null ) {
            List<AuthorityEntity> list = authorityListToAuthorityEntityList( dto.getAuthorities() );
            if ( list != null ) {
                entity.getAuthorities().clear();
                entity.getAuthorities().addAll( list );
            }
        }
        else {
            List<AuthorityEntity> list = authorityListToAuthorityEntityList( dto.getAuthorities() );
            if ( list != null ) {
                entity.setAuthorities( list );
            }
        }
    }

    protected AuthorityEntity authorityToAuthorityEntity(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityEntity authorityEntity = new AuthorityEntity();

        if ( authority.getAuthority() != null ) {
            authorityEntity.setAuthority( Enum.valueOf( Authority.class, authority.getAuthority() ) );
        }

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
