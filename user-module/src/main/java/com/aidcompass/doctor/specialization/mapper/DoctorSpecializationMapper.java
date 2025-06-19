package com.aidcompass.doctor.specialization.mapper;

import com.aidcompass.doctor.specialization.DoctorSpecializationService;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorSpecializationMapper {

    DoctorSpecializationEntity toEntity(DoctorSpecialization specialization);

    List<DoctorSpecializationEntity> toEntityList(List<DoctorSpecialization> specializations);

    default DoctorSpecialization toEnum(DoctorSpecializationEntity entity) {
        return entity == null ? null : entity.getSpecialization();
    }

    List<DoctorSpecialization> toEnumList(List<DoctorSpecializationEntity> entityList);
}
