package com.aidcompass.doctor.specialization.mapper;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.doctor.specialization.models.DoctorSpecializationEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class DoctorSpecializationMapperImpl implements DoctorSpecializationMapper {

    @Override
    public DoctorSpecializationEntity toEntity(DoctorSpecialization specialization) {
        if ( specialization == null ) {
            return null;
        }

        DoctorSpecializationEntity doctorSpecializationEntity = new DoctorSpecializationEntity();

        doctorSpecializationEntity.setSpecialization( specialization );

        return doctorSpecializationEntity;
    }

    @Override
    public List<DoctorSpecializationEntity> toEntityList(List<DoctorSpecialization> specializations) {
        if ( specializations == null ) {
            return null;
        }

        List<DoctorSpecializationEntity> list = new ArrayList<DoctorSpecializationEntity>( specializations.size() );
        for ( DoctorSpecialization doctorSpecialization : specializations ) {
            list.add( toEntity( doctorSpecialization ) );
        }

        return list;
    }

    @Override
    public List<DoctorSpecialization> toEnumList(List<DoctorSpecializationEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DoctorSpecialization> list = new ArrayList<DoctorSpecialization>( entityList.size() );
        for ( DoctorSpecializationEntity doctorSpecializationEntity : entityList ) {
            list.add( toEnum( doctorSpecializationEntity ) );
        }

        return list;
    }
}
