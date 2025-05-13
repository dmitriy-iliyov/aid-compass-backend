package com.aidcompass.doctor.specialization;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorSpecializationMapper {

    DoctorSpecializationEntity toEntity(DoctorSpecialization specialization);

    List<DoctorSpecializationEntity> toEntityList(List<DoctorSpecialization> specializations);
}
