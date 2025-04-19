package com.example.appointment;

import com.example.appointment.models.AppointmentResponseDto;
import com.example.appointment.models.BaseAppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "volunteer.id", target = "volunteerId")
    AppointmentResponseDto toResponseDto(BaseAppointmentEntity appointmentEntity);

    List<AppointmentResponseDto> toResponseDtoList(List<BaseAppointmentEntity> doctorAppointmentEntityList);
}
