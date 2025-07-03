package com.aidcompass.jurist.specialization.mapper;

import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristSpecializationEntity;
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
public class JuristSpecializationMapperImpl implements JuristSpecializationMapper {

    @Override
    public JuristSpecializationEntity toEntity(JuristSpecialization specialization) {
        if ( specialization == null ) {
            return null;
        }

        JuristSpecializationEntity juristSpecializationEntity = new JuristSpecializationEntity();

        juristSpecializationEntity.setSpecialization( specialization );

        return juristSpecializationEntity;
    }

    @Override
    public List<JuristSpecializationEntity> toEntityList(List<JuristSpecialization> specializations) {
        if ( specializations == null ) {
            return null;
        }

        List<JuristSpecializationEntity> list = new ArrayList<JuristSpecializationEntity>( specializations.size() );
        for ( JuristSpecialization juristSpecialization : specializations ) {
            list.add( toEntity( juristSpecialization ) );
        }

        return list;
    }

    @Override
    public List<JuristSpecialization> toEnumList(List<JuristSpecialization> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<JuristSpecialization> list = new ArrayList<JuristSpecialization>( entityList.size() );
        for ( JuristSpecialization juristSpecialization : entityList ) {
            list.add( juristSpecialization );
        }

        return list;
    }
}
