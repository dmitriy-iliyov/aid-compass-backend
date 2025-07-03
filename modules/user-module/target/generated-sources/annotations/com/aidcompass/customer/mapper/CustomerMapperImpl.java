package com.aidcompass.customer.mapper;

import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.PublicCustomerResponseDto;
import com.aidcompass.gender.Gender;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerEntity toEntity(UUID id, CustomerDto dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();

        if ( dto != null ) {
            customerEntity.setFirstName( dto.getFirstName() );
            customerEntity.setSecondName( dto.getSecondName() );
            customerEntity.setLastName( dto.getLastName() );
            customerEntity.setBirthdayDate( dto.getBirthdayDate() );
        }
        customerEntity.setId( id );
        customerEntity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );

        return customerEntity;
    }

    @Override
    public PublicCustomerResponseDto toPublicDto(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        LocalDate birthdayDate = null;
        Gender gender = null;

        id = entity.getId();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        birthdayDate = entity.getBirthdayDate();
        gender = entity.getGender();

        PublicCustomerResponseDto publicCustomerResponseDto = new PublicCustomerResponseDto( id, lastName, firstName, secondName, birthdayDate, gender );

        return publicCustomerResponseDto;
    }

    @Override
    public List<PublicCustomerResponseDto> toPublicDtoList(List<CustomerEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PublicCustomerResponseDto> list = new ArrayList<PublicCustomerResponseDto>( entityList.size() );
        for ( CustomerEntity customerEntity : entityList ) {
            list.add( toPublicDto( customerEntity ) );
        }

        return list;
    }

    @Override
    public PrivateCustomerResponseDto toPrivateDto(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProfileStatus profileStatus = null;
        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        LocalDate birthdayDate = null;
        Gender gender = null;

        profileStatus = entityProfileStatusEntityProfileStatus( entity );
        id = entity.getId();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        birthdayDate = entity.getBirthdayDate();
        gender = entity.getGender();

        PrivateCustomerResponseDto privateCustomerResponseDto = new PrivateCustomerResponseDto( id, lastName, firstName, secondName, birthdayDate, gender, profileStatus );

        return privateCustomerResponseDto;
    }

    @Override
    public void updateEntityFromDto(CustomerDto dto, CustomerEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getSecondName() != null ) {
            entity.setSecondName( dto.getSecondName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getBirthdayDate() != null ) {
            entity.setBirthdayDate( dto.getBirthdayDate() );
        }

        entity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );
    }

    private ProfileStatus entityProfileStatusEntityProfileStatus(CustomerEntity customerEntity) {
        if ( customerEntity == null ) {
            return null;
        }
        ProfileStatusEntity profileStatusEntity = customerEntity.getProfileStatusEntity();
        if ( profileStatusEntity == null ) {
            return null;
        }
        ProfileStatus profileStatus = profileStatusEntity.getProfileStatus();
        if ( profileStatus == null ) {
            return null;
        }
        return profileStatus;
    }
}
