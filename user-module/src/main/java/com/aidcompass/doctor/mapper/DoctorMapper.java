package com.aidcompass.doctor.mapper;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.DoctorDto;
import com.aidcompass.doctor.dto.PrivateDoctorResponseDto;
import com.aidcompass.doctor.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.mapper.DoctorSpecializationMapper;
import com.aidcompass.doctor.specialization.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.dto.BasePrivateVolunteerDto;
import com.aidcompass.dto.BaseSystemVolunteerDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DoctorSpecializationMapper.class, DetailMapper.class}
)
public interface DoctorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "specializations", source = "specializationEntityList")
    @Mapping(target = "gender", expression = "java(com.aidcompass.enums.gender.Gender.toEnum(dto.getGender()))")
    DoctorEntity toEntity(UUID id, List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto);

    @Mapping(target = "isApproved",  source = "approved")
    @Mapping(target = "profileStatus", source = "profileStatusEntity.profileStatus")
    @Mapping(target = "profileProgress",
            expression = "java((int) (100 * ((double) entity.getProfileProgress() / com.aidcompass.profile_status.ProfileConfig.MAX_PROFILE_PROGRESS)))")
    BasePrivateVolunteerDto toBaseDto(DoctorEntity entity);

    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "specializations", source = "specializations")
    PrivateDoctorResponseDto toPrivateDto(DoctorEntity entity);

    @Named("toPrivateDtoWithParam")
    @Mapping(target = "baseDto", expression = "java(toBaseDto(entity))")
    @Mapping(target = "specializations", source = "paramSpecializations")
    PrivateDoctorResponseDto toPrivateDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity);

    @Mapping(target = "specializations", source = "specializations")
    PublicDoctorResponseDto toPublicDto(DoctorEntity entity);

    @Mapping(target = "specializations", source = "paramSpecializations")
    PublicDoctorResponseDto toPublicDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specializations", source = "specializationEntityList")
    @Mapping(target = "gender", expression = "java(com.aidcompass.enums.gender.Gender.toEnum(dto.getGender()))")
    void updateEntityFromUpdateDto(List<DoctorSpecializationEntity> specializationEntityList, DoctorDto dto,
                                   @MappingTarget DoctorEntity entity);

    BaseSystemVolunteerDto toSystemDto(DoctorEntity doctorEntity);
}
