package aidcompass.api.user.mapper;

import aidcompass.api.general.utils.MapperUtils;
import aidcompass.api.security.models.Role;
import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.user.models.dto.UserUpdateDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-13T23:52:54+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private MapperUtils mapperUtils;

    @Override
    public UserEntity toEntity(UserRegistrationDto customerRegistrationDto) {
        if ( customerRegistrationDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setPassword( mapperUtils.encode( customerRegistrationDto.getPassword() ) );
        userEntity.setEmail( customerRegistrationDto.getEmail() );
        userEntity.setUsername( customerRegistrationDto.getUsername() );
        userEntity.setNumber( customerRegistrationDto.getNumber() );

        userEntity.setRole( Role.ROLE_USER );
        userEntity.setCreatedAt( java.time.Instant.now() );

        return userEntity;
    }

    @Override
    public UserResponseDto toResponseDto(UserEntity customerEntity) {
        if ( customerEntity == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setCreatedAt( mapperUtils.formatDate( customerEntity.getCreatedAt() ) );
        userResponseDto.setId( customerEntity.getId() );
        userResponseDto.setUsername( customerEntity.getUsername() );
        userResponseDto.setEmail( customerEntity.getEmail() );
        userResponseDto.setNumber( customerEntity.getNumber() );
        userResponseDto.setUpdatedAt( customerEntity.getUpdatedAt() );

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> toResponseDtoList(Iterable<UserEntity> customerEntities) {
        if ( customerEntities == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>();
        for ( UserEntity userEntity : customerEntities ) {
            list.add( toResponseDto( userEntity ) );
        }

        return list;
    }

    @Override
    public UserUpdateDto toUpdateDto(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        UserUpdateDto userUpdateDto = new UserUpdateDto();

        userUpdateDto.setUsername( userRegistrationDto.getUsername() );
        userUpdateDto.setEmail( userRegistrationDto.getEmail() );
        userUpdateDto.setPassword( userRegistrationDto.getPassword() );
        userUpdateDto.setNumber( userRegistrationDto.getNumber() );

        return userUpdateDto;
    }

    @Override
    public void updateEntityFromUpdateDto(UserUpdateDto userUpdateDto, UserEntity userEntity) {
        if ( userUpdateDto == null ) {
            return;
        }

        userEntity.setEmail( userUpdateDto.getEmail() );
        userEntity.setPassword( userUpdateDto.getPassword() );
        userEntity.setUsername( userUpdateDto.getUsername() );
        userEntity.setNumber( userUpdateDto.getNumber() );

        userEntity.setUpdatedAt( java.time.Instant.now() );
    }
}
