package com.aidcompass.doctor.specialization.mapper;

import com.aidcompass.doctor.specialization.DoctorSpecialization;
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

    default DoctorSpecialization toEnum(DoctorSpecializationEntity entity) {
        return entity == null ? null : entity.getSpecialization();
    }

    List<DoctorSpecialization> toEnumList(List<DoctorSpecializationEntity> entityList);
}
