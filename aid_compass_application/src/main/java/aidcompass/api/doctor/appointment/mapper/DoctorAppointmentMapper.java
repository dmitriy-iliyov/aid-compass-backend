package aidcompass.api.doctor.appointment.mapper;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorAppointmentMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    DoctorAppointmentEntity toEntity(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "volunteer.id", target = "volunteerId")
    DoctorAppointmentResponseDto toResponseDto(DoctorAppointmentEntity doctorAppointmentEntity);

    List<DoctorAppointmentResponseDto> toResponseDtoList(List<DoctorAppointmentEntity> doctorAppointmentEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    void updateEntityFromUpdateDto(DoctorAppointmentUpdateDto doctorAppointmentRegistrationDto,
                                   @MappingTarget DoctorAppointmentEntity doctorAppointmentEntity);
}
