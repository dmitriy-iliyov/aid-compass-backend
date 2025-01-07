package aidcompass.api.doctor.appointment.mapper;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorAppointmentMapper {

    DoctorAppointmentEntity toEntity(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "volunteer.id", target = "volunteerId")
    DoctorAppointmentResponseDto toResponseDto(DoctorAppointmentEntity doctorAppointmentEntity);

    List<DoctorAppointmentResponseDto> toResponseDtoList(List<DoctorAppointmentEntity> doctorAppointmentEntityList);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromUpdateDto(DoctorAppointmentUpdateDto doctorAppointmentRegistrationDto,
                                   @MappingTarget DoctorAppointmentEntity doctorAppointmentEntity);
}
