package com.aidcompass.jurist.mapper;

import com.aidcompass.gender.Gender;
import com.aidcompass.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.general.dto.BasePrivateVolunteerDto;
import com.aidcompass.jurist.models.JuristEntity;
import com.aidcompass.jurist.models.dto.JuristDto;
import com.aidcompass.jurist.models.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.specialization.mapper.JuristSpecializationMapper;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristSpecializationEntity;
import com.aidcompass.jurist.specialization.models.JuristType;
import com.aidcompass.jurist.specialization.models.JuristTypeEntity;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class JuristMapperImpl implements JuristMapper {

    @Autowired
    private JuristSpecializationMapper juristSpecializationMapper;

    @Override
    public JuristEntity toEntity(UUID id, JuristDto dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        JuristEntity juristEntity = new JuristEntity();

        if ( dto != null ) {
            juristEntity.setFirstName( dto.getFirstName() );
            juristEntity.setSecondName( dto.getSecondName() );
            juristEntity.setLastName( dto.getLastName() );
            juristEntity.setSpecializationDetail( dto.getSpecializationDetail() );
            juristEntity.setWorkingExperience( dto.getWorkingExperience() );
        }
        juristEntity.setId( id );
        juristEntity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );

        return juristEntity;
    }

    @Override
    public BasePrivateVolunteerDto toBaseDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        boolean isApproved = false;
        String profileStatus = null;
        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        Gender gender = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        isApproved = entity.isApproved();
        ProfileStatus profileStatus1 = entityProfileStatusEntityProfileStatus( entity );
        if ( profileStatus1 != null ) {
            profileStatus = profileStatus1.name();
        }
        id = entity.getId();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        gender = entity.getGender();
        specializationDetail = entity.getSpecializationDetail();
        workingExperience = entity.getWorkingExperience();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();

        int profileProgress = (int) (100 * ((double) entity.getProfileProgress() / com.aidcompass.profile_status.ProfileConfig.MAX_PROFILE_PROGRESS));

        BasePrivateVolunteerDto basePrivateVolunteerDto = new BasePrivateVolunteerDto( id, lastName, firstName, secondName, gender, specializationDetail, workingExperience, isApproved, profileProgress, profileStatus, createdAt, updatedAt );

        return basePrivateVolunteerDto;
    }

    @Override
    public PrivateJuristResponseDto toPrivateDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        JuristType type = null;
        List<JuristSpecialization> specializations = null;

        type = entityTypeEntityType( entity );
        specializations = juristSpecializationEntityListToJuristSpecializationList( entity.getSpecializations() );

        BasePrivateVolunteerDto baseDto = toBaseDto(entity);

        PrivateJuristResponseDto privateJuristResponseDto = new PrivateJuristResponseDto( baseDto, type, specializations );

        return privateJuristResponseDto;
    }

    @Override
    public PrivateJuristResponseDto toPrivateDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        JuristType type = null;
        if ( entity != null ) {
            type = entityTypeEntityType( entity );
        }
        List<JuristSpecialization> specializations = null;
        specializations = juristSpecializationMapper.toEnumList( paramSpecializations );

        BasePrivateVolunteerDto baseDto = toBaseDto(entity);

        PrivateJuristResponseDto privateJuristResponseDto = new PrivateJuristResponseDto( baseDto, type, specializations );

        return privateJuristResponseDto;
    }

    @Override
    public PublicJuristResponseDto toPublicDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        JuristType type = null;
        List<JuristSpecialization> specializations = null;
        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;

        type = entityTypeEntityType( entity );
        specializations = juristSpecializationEntityListToJuristSpecializationList( entity.getSpecializations() );
        id = entity.getId();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        specializationDetail = entity.getSpecializationDetail();
        workingExperience = entity.getWorkingExperience();
        gender = entity.getGender();

        PublicJuristResponseDto publicJuristResponseDto = new PublicJuristResponseDto( id, lastName, firstName, secondName, type, specializations, specializationDetail, workingExperience, gender );

        return publicJuristResponseDto;
    }

    @Override
    public PublicJuristResponseDto toPublicDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        JuristType type = null;
        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;
        if ( entity != null ) {
            type = entityTypeEntityType( entity );
            id = entity.getId();
            lastName = entity.getLastName();
            firstName = entity.getFirstName();
            secondName = entity.getSecondName();
            specializationDetail = entity.getSpecializationDetail();
            workingExperience = entity.getWorkingExperience();
            gender = entity.getGender();
        }
        List<JuristSpecialization> specializations = null;
        specializations = juristSpecializationMapper.toEnumList( paramSpecializations );

        PublicJuristResponseDto publicJuristResponseDto = new PublicJuristResponseDto( id, lastName, firstName, secondName, type, specializations, specializationDetail, workingExperience, gender );

        return publicJuristResponseDto;
    }

    @Override
    public void updateEntityFromUpdateDto(JuristDto dto, JuristEntity entity) {
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
        if ( dto.getSpecializationDetail() != null ) {
            entity.setSpecializationDetail( dto.getSpecializationDetail() );
        }
        if ( dto.getWorkingExperience() != null ) {
            entity.setWorkingExperience( dto.getWorkingExperience() );
        }

        entity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );
    }

    @Override
    public BaseSystemVolunteerDto toSystemDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String firstName = null;
        String secondName = null;
        String lastName = null;

        id = entity.getId();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        lastName = entity.getLastName();

        BaseSystemVolunteerDto baseSystemVolunteerDto = new BaseSystemVolunteerDto( id, firstName, secondName, lastName );

        return baseSystemVolunteerDto;
    }

    private ProfileStatus entityProfileStatusEntityProfileStatus(JuristEntity juristEntity) {
        if ( juristEntity == null ) {
            return null;
        }
        ProfileStatusEntity profileStatusEntity = juristEntity.getProfileStatusEntity();
        if ( profileStatusEntity == null ) {
            return null;
        }
        ProfileStatus profileStatus = profileStatusEntity.getProfileStatus();
        if ( profileStatus == null ) {
            return null;
        }
        return profileStatus;
    }

    private JuristType entityTypeEntityType(JuristEntity juristEntity) {
        if ( juristEntity == null ) {
            return null;
        }
        JuristTypeEntity typeEntity = juristEntity.getTypeEntity();
        if ( typeEntity == null ) {
            return null;
        }
        JuristType type = typeEntity.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }

    protected List<JuristSpecialization> juristSpecializationEntityListToJuristSpecializationList(List<JuristSpecializationEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<JuristSpecialization> list1 = new ArrayList<JuristSpecialization>( list.size() );
        for ( JuristSpecializationEntity juristSpecializationEntity : list ) {
            list1.add( juristSpecializationMapper.toEnum( juristSpecializationEntity ) );
        }

        return list1;
    }
}
