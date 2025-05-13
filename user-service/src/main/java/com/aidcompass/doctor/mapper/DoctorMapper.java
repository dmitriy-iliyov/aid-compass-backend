package com.aidcompass.doctor.mapper;

import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    DoctorEntity toEntity(UUID id, DoctorRegistrationDto dto);

    PrivateDoctorResponseDto toPrivateDto(DoctorEntity entity);

    List<PrivateDoctorResponseDto> toPrivateDtoList(List<DoctorEntity> entityList);

    PublicDoctorResponseDto toPublicDto(DoctorEntity entity);

    List<PublicDoctorResponseDto> toPublicDtoList(List<DoctorEntity> entityList);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromUpdateDto(DoctorUpdateDto dto, @MappingTarget DoctorEntity entity);
}
