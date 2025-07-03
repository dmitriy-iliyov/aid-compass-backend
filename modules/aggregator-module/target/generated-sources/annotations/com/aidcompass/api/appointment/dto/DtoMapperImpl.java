package com.aidcompass.api.appointment.dto;

import com.aidcompass.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.gender.Gender;
import com.aidcompass.jurist.models.dto.PublicJuristResponseDto;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:51+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public PublicVolunteerDto toVolunteerDto(PublicDoctorResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        List<String> specializations = null;
        UUID id = null;
        String firstName = null;
        String secondName = null;
        String lastName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;

        specializations = translate( dto.specializations() );
        id = dto.id();
        firstName = dto.firstName();
        secondName = dto.secondName();
        lastName = dto.lastName();
        specializationDetail = dto.specializationDetail();
        workingExperience = dto.workingExperience();
        gender = dto.gender();

        PublicVolunteerDto publicVolunteerDto = new PublicVolunteerDto( id, firstName, secondName, lastName, specializations, specializationDetail, workingExperience, gender );

        return publicVolunteerDto;
    }

    @Override
    public PublicVolunteerDto toVolunteerDto(PublicJuristResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        List<String> specializations = null;
        UUID id = null;
        String firstName = null;
        String secondName = null;
        String lastName = null;
        String specializationDetail = null;
        Integer workingExperience = null;
        Gender gender = null;

        specializations = translateJurist( dto.specializations() );
        id = dto.id();
        firstName = dto.firstName();
        secondName = dto.secondName();
        lastName = dto.lastName();
        specializationDetail = dto.specializationDetail();
        workingExperience = dto.workingExperience();
        gender = dto.gender();

        PublicVolunteerDto publicVolunteerDto = new PublicVolunteerDto( id, firstName, secondName, lastName, specializations, specializationDetail, workingExperience, gender );

        return publicVolunteerDto;
    }
}
