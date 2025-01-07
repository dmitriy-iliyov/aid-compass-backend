package aidcompass.api.doctor.appointment.mapper;

import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;
import aidcompass.api.general.models.VolunteerBaseEntity;
import aidcompass.api.user.models.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-13T23:52:54+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class DoctorAppointmentMapperImpl implements DoctorAppointmentMapper {

    @Override
    public DoctorAppointmentEntity toEntity(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto) {
        if ( doctorAppointmentRegistrationDto == null ) {
            return null;
        }

        DoctorAppointmentEntity doctorAppointmentEntity = new DoctorAppointmentEntity();

        doctorAppointmentEntity.setAppointmentDate( doctorAppointmentRegistrationDto.getAppointmentDate() );
        doctorAppointmentEntity.setTopic( doctorAppointmentRegistrationDto.getTopic() );
        doctorAppointmentEntity.setDescription( doctorAppointmentRegistrationDto.getDescription() );

        doctorAppointmentEntity.setCreatedAt( java.time.Instant.now() );

        return doctorAppointmentEntity;
    }

    @Override
    public DoctorAppointmentResponseDto toResponseDto(DoctorAppointmentEntity doctorAppointmentEntity) {
        if ( doctorAppointmentEntity == null ) {
            return null;
        }

        DoctorAppointmentResponseDto doctorAppointmentResponseDto = new DoctorAppointmentResponseDto();

        doctorAppointmentResponseDto.setUserId( doctorAppointmentEntityUserId( doctorAppointmentEntity ) );
        doctorAppointmentResponseDto.setVolunteerId( doctorAppointmentEntityVolunteerId( doctorAppointmentEntity ) );
        doctorAppointmentResponseDto.setId( doctorAppointmentEntity.getId() );
        doctorAppointmentResponseDto.setAppointmentDate( doctorAppointmentEntity.getAppointmentDate() );
        doctorAppointmentResponseDto.setTopic( doctorAppointmentEntity.getTopic() );
        doctorAppointmentResponseDto.setDescription( doctorAppointmentEntity.getDescription() );

        return doctorAppointmentResponseDto;
    }

    @Override
    public List<DoctorAppointmentResponseDto> toResponseDtoList(List<DoctorAppointmentEntity> doctorAppointmentEntityList) {
        if ( doctorAppointmentEntityList == null ) {
            return null;
        }

        List<DoctorAppointmentResponseDto> list = new ArrayList<DoctorAppointmentResponseDto>( doctorAppointmentEntityList.size() );
        for ( DoctorAppointmentEntity doctorAppointmentEntity : doctorAppointmentEntityList ) {
            list.add( toResponseDto( doctorAppointmentEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromUpdateDto(DoctorAppointmentUpdateDto doctorAppointmentRegistrationDto, DoctorAppointmentEntity doctorAppointmentEntity) {
        if ( doctorAppointmentRegistrationDto == null ) {
            return;
        }

        doctorAppointmentEntity.setAppointmentDate( doctorAppointmentRegistrationDto.getAppointmentDate() );
        doctorAppointmentEntity.setTopic( doctorAppointmentRegistrationDto.getTopic() );
        doctorAppointmentEntity.setDescription( doctorAppointmentRegistrationDto.getDescription() );

        doctorAppointmentEntity.setUpdatedAt( java.time.Instant.now() );
    }

    private Long doctorAppointmentEntityUserId(DoctorAppointmentEntity doctorAppointmentEntity) {
        if ( doctorAppointmentEntity == null ) {
            return null;
        }
        UserEntity user = doctorAppointmentEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long doctorAppointmentEntityVolunteerId(DoctorAppointmentEntity doctorAppointmentEntity) {
        if ( doctorAppointmentEntity == null ) {
            return null;
        }
        VolunteerBaseEntity volunteer = doctorAppointmentEntity.getVolunteer();
        if ( volunteer == null ) {
            return null;
        }
        Long id = volunteer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
