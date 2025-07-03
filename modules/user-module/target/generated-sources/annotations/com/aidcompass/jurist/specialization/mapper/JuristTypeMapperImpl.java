package com.aidcompass.jurist.specialization.mapper;

import com.aidcompass.jurist.specialization.models.JuristType;
import com.aidcompass.jurist.specialization.models.JuristTypeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class JuristTypeMapperImpl implements JuristTypeMapper {

    @Override
    public JuristTypeEntity toEntity(JuristType type) {
        if ( type == null ) {
            return null;
        }

        JuristTypeEntity juristTypeEntity = new JuristTypeEntity();

        juristTypeEntity.setType( type );

        return juristTypeEntity;
    }

    @Override
    public List<JuristTypeEntity> toEntityList(List<JuristType> typeList) {
        if ( typeList == null ) {
            return null;
        }

        List<JuristTypeEntity> list = new ArrayList<JuristTypeEntity>( typeList.size() );
        for ( JuristType juristType : typeList ) {
            list.add( toEntity( juristType ) );
        }

        return list;
    }
}
