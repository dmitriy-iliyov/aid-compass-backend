package com.aidcompass.appointment.mapper;

import com.aidcompass.appointment.models.AppointmentEntity;
import com.aidcompass.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import com.aidcompass.appointment.models.enums.AppointmentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:45+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentEntity toEntity(UUID customerId, LocalTime end, AppointmentCreateDto dto) {
        if ( customerId == null && end == null && dto == null ) {
            return null;
        }

        AppointmentEntity appointmentEntity = new AppointmentEntity();

        if ( dto != null ) {
            appointmentEntity.setVolunteerId( dto.volunteerId() );
            appointmentEntity.setStart( dto.start() );
            appointmentEntity.setDate( dto.date() );
            appointmentEntity.setDescription( dto.description() );
        }
        appointmentEntity.setCustomerId( customerId );
        appointmentEntity.setEnd( end );
        appointmentEntity.setType( com.aidcompass.appointment.models.enums.AppointmentType.valueOf(dto.type()) );

        return appointmentEntity;
    }

    @Override
    public AppointmentResponseDto toDto(AppointmentEntity dto) {
        if ( dto == null ) {
            return null;
        }

        Long id = null;
        UUID customerId = null;
        UUID volunteerId = null;
        LocalDate date = null;
        LocalTime start = null;
        LocalTime end = null;
        AppointmentType type = null;
        AppointmentStatus status = null;
        String description = null;

        id = dto.getId();
        customerId = dto.getCustomerId();
        volunteerId = dto.getVolunteerId();
        date = dto.getDate();
        start = dto.getStart();
        end = dto.getEnd();
        type = dto.getType();
        status = dto.getStatus();
        description = dto.getDescription();

        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto( id, customerId, volunteerId, date, start, end, type, status, description );

        return appointmentResponseDto;
    }

    @Override
    public List<AppointmentResponseDto> toDtoList(List<AppointmentEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppointmentResponseDto> list = new ArrayList<AppointmentResponseDto>( entityList.size() );
        for ( AppointmentEntity appointmentEntity : entityList ) {
            list.add( toDto( appointmentEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromUpdateDto(AppointmentUpdateDto dto, LocalTime end, AppointmentEntity entity) {
        if ( dto == null && end == null ) {
            return;
        }

        if ( dto != null ) {
            entity.setStart( dto.getStart() );
            entity.setDate( dto.getDate() );
        }
        entity.setEnd( end );
    }
}
