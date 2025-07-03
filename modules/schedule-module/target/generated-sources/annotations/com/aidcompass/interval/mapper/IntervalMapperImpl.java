package com.aidcompass.interval.mapper;

import com.aidcompass.interval.models.IntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.dto.SystemIntervalUpdateDto;
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
public class IntervalMapperImpl implements IntervalMapper {

    @Override
    public IntervalEntity toEntity(UUID ownerId, SystemIntervalCreatedDto dto) {
        if ( ownerId == null && dto == null ) {
            return null;
        }

        IntervalEntity intervalEntity = new IntervalEntity();

        if ( dto != null ) {
            intervalEntity.setStart( dto.start() );
            intervalEntity.setEnd( dto.end() );
            intervalEntity.setDate( dto.date() );
        }
        intervalEntity.setOwnerId( ownerId );

        return intervalEntity;
    }

    @Override
    public IntervalResponseDto toDto(IntervalEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        UUID ownerId = null;
        LocalTime start = null;
        LocalTime end = null;
        LocalDate date = null;

        id = entity.getId();
        ownerId = entity.getOwnerId();
        start = entity.getStart();
        end = entity.getEnd();
        date = entity.getDate();

        IntervalResponseDto intervalResponseDto = new IntervalResponseDto( id, ownerId, start, end, date );

        return intervalResponseDto;
    }

    @Override
    public List<IntervalResponseDto> toDtoList(List<IntervalEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<IntervalResponseDto> list = new ArrayList<IntervalResponseDto>( entityList.size() );
        for ( IntervalEntity intervalEntity : entityList ) {
            list.add( toDto( intervalEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(SystemIntervalUpdateDto dto, IntervalEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.start() != null ) {
            entity.setStart( dto.start() );
        }
        if ( dto.end() != null ) {
            entity.setEnd( dto.end() );
        }
        if ( dto.date() != null ) {
            entity.setDate( dto.date() );
        }
    }
}
