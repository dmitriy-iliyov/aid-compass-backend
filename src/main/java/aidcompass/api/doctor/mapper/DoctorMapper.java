package aidcompass.api.doctor.mapper;

import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.doctor.models.dto.DoctorRegistrationDto;
import aidcompass.api.doctor.models.dto.DoctorResponseDto;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;
import aidcompass.api.general.utils.MapperUtils;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class})
public interface DoctorMapper {

    DoctorEntity toEntity(DoctorRegistrationDto doctorRegistrationDto);

    DoctorResponseDto toResponseDto(DoctorEntity doctorEntity);

    List<DoctorResponseDto> toResponseDtoList(Iterable<DoctorEntity> doctorEntities);

    DoctorUpdateDto toUpdateDto(DoctorRegistrationDto doctorRegistrationDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromUpdateDto(DoctorUpdateDto doctorUpdateDto, @MappingTarget DoctorEntity entity);
}
