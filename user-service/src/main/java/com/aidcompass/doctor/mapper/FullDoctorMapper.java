package com.aidcompass.doctor.mapper;


import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DoctorMapper.class, DetailMapper.class}
)
public interface FullDoctorMapper {

    @Mapping(target = "doctor", source = "entity", qualifiedByName = "toPrivateDto")
    @Mapping(target = "detail", source = "entity.detailEntity", qualifiedByName = "toPrivateDetailDto")
    FullPrivateDoctorResponseDto toFullPrivateDto(DoctorEntity entity);

    @Mapping(target = "doctor", source = "entity", qualifiedByName = "toPublicDto")
    @Mapping(target = "detail", source = "entity.detailEntity", qualifiedByName = "toPublicDetailDto")
    FullPublicDoctorResponseDto toFullPublicDto(DoctorEntity entity);
}
