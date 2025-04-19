package com.example.doctor.mapper;

import com.example.doctor.models.DoctorEntity;
import com.example.doctor.models.dto.DoctorResponseDto;
import com.example.doctor.models.dto.DoctorUpdateDto;
import com.example.models.dto.DoctorRegistrationDto;
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
