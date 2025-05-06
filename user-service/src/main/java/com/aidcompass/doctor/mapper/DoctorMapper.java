package com.aidcompass.doctor.mapper;

import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.DoctorUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    DoctorEntity toEntity(DoctorRegistrationDto doctorRegistrationDto);

    DoctorResponseDto toResponseDto(DoctorEntity doctorEntity);

    List<DoctorResponseDto> toResponseDtoList(Iterable<DoctorEntity> doctorEntities);

    DoctorUpdateDto toUpdateDto(DoctorRegistrationDto doctorRegistrationDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromUpdateDto(DoctorUpdateDto doctorUpdateDto, @MappingTarget DoctorEntity entity);
}
