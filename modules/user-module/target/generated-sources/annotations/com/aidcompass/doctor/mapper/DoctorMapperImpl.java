package com.aidcompass.doctor.mapper;

import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.DoctorDto;
import com.aidcompass.doctor.models.dto.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.gender.Gender;
import com.aidcompass.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.general.dto.BasePrivateVolunteerDto;
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
public class DoctorMapperImpl implements DoctorMapper {

    @Autowired
    private DoctorSpecializationMapper doctorSpecializationMapper;

    @Override
    public DoctorEntity toEntity(UUID id, List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto) {
        if ( id == null && specializationEntityList == null && dto == null ) {
            return null;
        }

        DoctorEntity doctorEntity = new DoctorEntity();

        if ( dto != null ) {
            doctorEntity.setFirstName( dto.getFirstName() );
            doctorEntity.setSecondName( dto.getSecondName() );
            doctorEntity.setLastName( dto.getLastName() );
            doctorEntity.setSpecializationDetail( dto.getSpecializationDetail() );
            doctorEntity.setWorkingExperience( dto.getWorkingExperience() );
        }
        doctorEntity.setId( id );
        List<DoctorSpecializationEntity> list = specializationEntityList;
        if ( list != null ) {
            doctorEntity.setSpecializations( new ArrayList<DoctorSpecializationEntity>( list ) );
        }
        doctorEntity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );

        return doctorEntity;
    }

    @Override
    public BasePrivateVolunteerDto toBaseDto(DoctorEntity entity) {
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
    public PrivateDoctorResponseDto toPrivateDto(DoctorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<DoctorSpecialization> specializations = null;

        specializations = doctorSpecializationMapper.toEnumList( entity.getSpecializations() );

        BasePrivateVolunteerDto baseDto = toBaseDto(entity);

        PrivateDoctorResponseDto privateDoctorResponseDto = new PrivateDoctorResponseDto( baseDto, specializations );

        return privateDoctorResponseDto;
    }

    @Override
    public PrivateDoctorResponseDto toPrivateDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        List<DoctorSpecialization> specializations = null;
        List<DoctorSpecialization> list = paramSpecializations;
        if ( list != null ) {
            specializations = new ArrayList<DoctorSpecialization>( list );
        }

        BasePrivateVolunteerDto baseDto = toBaseDto(entity);

        PrivateDoctorResponseDto privateDoctorResponseDto = new PrivateDoctorResponseDto( baseDto, specializations );

        return privateDoctorResponseDto;
    }

    @Override
    public PublicDoctorResponseDto toPublicDto(DoctorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<DoctorSpecialization> specializations = null;
        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;

        specializations = doctorSpecializationMapper.toEnumList( entity.getSpecializations() );
        id = entity.getId();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        secondName = entity.getSecondName();
        specializationDetail = entity.getSpecializationDetail();
        workingExperience = entity.getWorkingExperience();
        gender = entity.getGender();

        PublicDoctorResponseDto publicDoctorResponseDto = new PublicDoctorResponseDto( id, lastName, firstName, secondName, specializations, specializationDetail, workingExperience, gender );

        return publicDoctorResponseDto;
    }

    @Override
    public PublicDoctorResponseDto toPublicDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        UUID id = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;
        if ( entity != null ) {
            id = entity.getId();
            lastName = entity.getLastName();
            firstName = entity.getFirstName();
            secondName = entity.getSecondName();
            specializationDetail = entity.getSpecializationDetail();
            workingExperience = entity.getWorkingExperience();
            gender = entity.getGender();
        }
        List<DoctorSpecialization> specializations = null;
        List<DoctorSpecialization> list = paramSpecializations;
        if ( list != null ) {
            specializations = new ArrayList<DoctorSpecialization>( list );
        }

        PublicDoctorResponseDto publicDoctorResponseDto = new PublicDoctorResponseDto( id, lastName, firstName, secondName, specializations, specializationDetail, workingExperience, gender );

        return publicDoctorResponseDto;
    }

    @Override
    public void updateEntityFromUpdateDto(List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto, DoctorEntity entity) {
        if ( specializationEntityList == null && dto == null ) {
            return;
        }

        if ( dto != null ) {
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
        }
        if ( entity.getSpecializations() != null ) {
            List<DoctorSpecializationEntity> list = specializationEntityList;
            if ( list != null ) {
                entity.getSpecializations().clear();
                entity.getSpecializations().addAll( list );
            }
        }
        else {
            List<DoctorSpecializationEntity> list = specializationEntityList;
            if ( list != null ) {
                entity.setSpecializations( new ArrayList<DoctorSpecializationEntity>( list ) );
            }
        }
        entity.setGender( com.aidcompass.gender.Gender.toEnum(dto.getGender()) );
    }

    @Override
    public BaseSystemVolunteerDto toSystemDto(DoctorEntity doctorEntity) {
        if ( doctorEntity == null ) {
            return null;
        }

        UUID id = null;
        String firstName = null;
        String secondName = null;
        String lastName = null;

        id = doctorEntity.getId();
        firstName = doctorEntity.getFirstName();
        secondName = doctorEntity.getSecondName();
        lastName = doctorEntity.getLastName();

        BaseSystemVolunteerDto baseSystemVolunteerDto = new BaseSystemVolunteerDto( id, firstName, secondName, lastName );

        return baseSystemVolunteerDto;
    }

    private ProfileStatus entityProfileStatusEntityProfileStatus(DoctorEntity doctorEntity) {
        if ( doctorEntity == null ) {
            return null;
        }
        ProfileStatusEntity profileStatusEntity = doctorEntity.getProfileStatusEntity();
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
