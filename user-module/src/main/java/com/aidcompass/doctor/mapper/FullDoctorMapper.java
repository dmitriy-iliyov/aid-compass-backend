package com.aidcompass.doctor.mapper;


import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DoctorMapper.class, DetailMapper.class}
)
public interface FullDoctorMapper {
    @Mapping(target = "doctor", expression = "java(doctorMapper.toPrivateDto(entity))")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPrivateDoctorResponseDto toFullPrivateDto(DoctorEntity entity);

    @Mapping(target = "doctor", expression = "java(doctorMapper.toPrivateDto(paramSpecializations, entity))")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPrivateDoctorResponseDto toFullPrivateDto(List<DoctorSpecialization> paramSpecializations, DoctorEntity entity);


    @Mapping(target = "doctor", source = "entity")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPublicDoctorResponseDto toFullPublicDto(DoctorEntity entity);
}
