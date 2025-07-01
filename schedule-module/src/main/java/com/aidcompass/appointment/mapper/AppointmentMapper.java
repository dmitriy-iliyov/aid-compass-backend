package com.aidcompass.appointment.mapper;


import com.aidcompass.appointment.dto.AppointmentCreateDto;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.appointment.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.models.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {

    @Mapping(target = "type", expression = "java(com.aidcompass.appointment.enums.AppointmentType.valueOf(dto.type()))")
    AppointmentEntity toEntity(UUID customerId, LocalTime end, AppointmentCreateDto dto);

    AppointmentResponseDto toDto(AppointmentEntity dto);

    List<AppointmentResponseDto> toDtoList(List<AppointmentEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "volunteerId", ignore = true)
    void updateEntityFromUpdateDto(AppointmentUpdateDto dto, LocalTime end, @MappingTarget AppointmentEntity entity);
}