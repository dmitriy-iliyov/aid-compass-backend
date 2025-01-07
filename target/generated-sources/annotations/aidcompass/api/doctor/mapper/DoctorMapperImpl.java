package aidcompass.api.doctor.mapper;

import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.doctor.models.dto.DoctorRegistrationDto;
import aidcompass.api.doctor.models.dto.DoctorResponseDto;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-07T19:22:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public DoctorEntity toEntity(DoctorRegistrationDto doctorRegistrationDto) {
        if ( doctorRegistrationDto == null ) {
            return null;
        }

        DoctorEntity doctorEntity = new DoctorEntity();

        doctorEntity.setEmail( doctorRegistrationDto.getEmail() );
        doctorEntity.setUsername( doctorRegistrationDto.getUsername() );
        doctorEntity.setNumber( doctorRegistrationDto.getNumber() );
        doctorEntity.setLicenseNumber( doctorRegistrationDto.getLicenseNumber() );
        doctorEntity.setSpecialization( doctorRegistrationDto.getSpecialization() );
        doctorEntity.setYearsOfExperience( doctorRegistrationDto.getYearsOfExperience() );
        doctorEntity.setAddress( doctorRegistrationDto.getAddress() );
        doctorEntity.setAchievements( doctorRegistrationDto.getAchievements() );

        return doctorEntity;
    }

    @Override
    public DoctorResponseDto toResponseDto(DoctorEntity doctorEntity) {
        if ( doctorEntity == null ) {
            return null;
        }

        DoctorResponseDto doctorResponseDto = new DoctorResponseDto();

        doctorResponseDto.setId( doctorEntity.getId() );
        doctorResponseDto.setUsername( doctorEntity.getUsername() );
        doctorResponseDto.setEmail( doctorEntity.getEmail() );
        doctorResponseDto.setNumber( doctorEntity.getNumber() );
        if ( doctorEntity.getCreatedAt() != null ) {
            doctorResponseDto.setCreatedAt( doctorEntity.getCreatedAt().toString() );
        }
        doctorResponseDto.setUpdatedAt( doctorEntity.getUpdatedAt() );
        doctorResponseDto.setLicenseNumber( doctorEntity.getLicenseNumber() );
        doctorResponseDto.setSpecialization( doctorEntity.getSpecialization() );
        doctorResponseDto.setYearsOfExperience( doctorEntity.getYearsOfExperience() );
        doctorResponseDto.setAddress( doctorEntity.getAddress() );
        doctorResponseDto.setAchievements( doctorEntity.getAchievements() );
        doctorResponseDto.setProfilePictureUrl( doctorEntity.getProfilePictureUrl() );

        return doctorResponseDto;
    }

    @Override
    public List<DoctorResponseDto> toResponseDtoList(Iterable<DoctorEntity> doctorEntities) {
        if ( doctorEntities == null ) {
            return null;
        }

        List<DoctorResponseDto> list = new ArrayList<DoctorResponseDto>();
        for ( DoctorEntity doctorEntity : doctorEntities ) {
            list.add( toResponseDto( doctorEntity ) );
        }

        return list;
    }

    @Override
    public DoctorUpdateDto toUpdateDto(DoctorRegistrationDto doctorRegistrationDto) {
        if ( doctorRegistrationDto == null ) {
            return null;
        }

        DoctorUpdateDto doctorUpdateDto = new DoctorUpdateDto();

        doctorUpdateDto.setUsername( doctorRegistrationDto.getUsername() );
        doctorUpdateDto.setEmail( doctorRegistrationDto.getEmail() );
        doctorUpdateDto.setPassword( doctorRegistrationDto.getPassword() );
        doctorUpdateDto.setNumber( doctorRegistrationDto.getNumber() );
        doctorUpdateDto.setLicenseNumber( doctorRegistrationDto.getLicenseNumber() );
        doctorUpdateDto.setSpecialization( doctorRegistrationDto.getSpecialization() );
        doctorUpdateDto.setYearsOfExperience( doctorRegistrationDto.getYearsOfExperience() );
        doctorUpdateDto.setAddress( doctorRegistrationDto.getAddress() );
        doctorUpdateDto.setAchievements( doctorRegistrationDto.getAchievements() );

        return doctorUpdateDto;
    }

    @Override
    public void updateEntityFromUpdateDto(DoctorUpdateDto doctorUpdateDto, DoctorEntity entity) {
        if ( doctorUpdateDto == null ) {
            return;
        }

        if ( doctorUpdateDto.getEmail() != null ) {
            entity.setEmail( doctorUpdateDto.getEmail() );
        }
        if ( doctorUpdateDto.getUsername() != null ) {
            entity.setUsername( doctorUpdateDto.getUsername() );
        }
        if ( doctorUpdateDto.getNumber() != null ) {
            entity.setNumber( doctorUpdateDto.getNumber() );
        }
        if ( doctorUpdateDto.getLicenseNumber() != null ) {
            entity.setLicenseNumber( doctorUpdateDto.getLicenseNumber() );
        }
        if ( doctorUpdateDto.getSpecialization() != null ) {
            entity.setSpecialization( doctorUpdateDto.getSpecialization() );
        }
        if ( doctorUpdateDto.getYearsOfExperience() != null ) {
            entity.setYearsOfExperience( doctorUpdateDto.getYearsOfExperience() );
        }
        if ( doctorUpdateDto.getAddress() != null ) {
            entity.setAddress( doctorUpdateDto.getAddress() );
        }
        if ( doctorUpdateDto.getAchievements() != null ) {
            entity.setAchievements( doctorUpdateDto.getAchievements() );
        }
    }
}
