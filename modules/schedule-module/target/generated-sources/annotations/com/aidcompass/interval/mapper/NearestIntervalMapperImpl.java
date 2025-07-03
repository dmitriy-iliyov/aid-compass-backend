package com.aidcompass.interval.mapper;

import com.aidcompass.interval.models.NearestIntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:45+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class NearestIntervalMapperImpl implements NearestIntervalMapper {

    @Override
    public NearestIntervalEntity toEntity(IntervalResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        UUID ownerId = null;
        Long id = null;
        LocalTime start = null;
        LocalDate date = null;

        ownerId = dto.ownerId();
        id = dto.id();
        start = dto.start();
        date = dto.date();

        NearestIntervalEntity nearestIntervalEntity = new NearestIntervalEntity( id, ownerId, start, date );

        return nearestIntervalEntity;
    }

    @Override
    public NearestIntervalDto toDto(NearestIntervalEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        UUID ownerId = null;
        LocalTime start = null;
        LocalDate date = null;

        id = entity.getId();
        ownerId = entity.getOwnerId();
        start = entity.getStart();
        date = entity.getDate();

        NearestIntervalDto nearestIntervalDto = new NearestIntervalDto( id, ownerId, start, date );

        return nearestIntervalDto;
    }

    @Override
    public List<NearestIntervalDto> toDtoList(Iterable<NearestIntervalEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NearestIntervalDto> list = new ArrayList<NearestIntervalDto>();
        for ( NearestIntervalEntity nearestIntervalEntity : entityList ) {
            list.add( toDto( nearestIntervalEntity ) );
        }

        return list;
    }
}
