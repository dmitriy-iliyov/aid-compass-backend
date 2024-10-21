package aidcompass.api.doctor.mapper;

import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.doctor.models.DoctorRegistrationDto;
import aidcompass.api.doctor.models.DoctorResponseDto;
import aidcompass.api.doctor.models.DoctorUpdateDto;
import aidcompass.api.general.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MapperUtils.class})
public interface DoctorMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "approved", constant = "false")
    DoctorEntity toEntity(DoctorRegistrationDto doctorRegistrationDto);

    DoctorResponseDto toResponseDto(DoctorEntity doctorEntity);

    List<DoctorResponseDto> toResponseDtoList(Iterable<DoctorEntity> doctorEntities);

    DoctorUpdateDto toUpdateDto(DoctorRegistrationDto doctorRegistrationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    void updateEntityFromUpdateDto(DoctorUpdateDto doctorUpdateDto, @MappingTarget DoctorEntity entity);
}
