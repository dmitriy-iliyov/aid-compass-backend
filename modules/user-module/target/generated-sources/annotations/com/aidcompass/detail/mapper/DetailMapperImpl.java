package com.aidcompass.detail.mapper;

import com.aidcompass.detail.models.DetailDto;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.PrivateDetailResponseDto;
import com.aidcompass.detail.models.PublicDetailResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class DetailMapperImpl implements DetailMapper {

    @Override
    public PrivateDetailResponseDto toPrivateDetailDto(DetailEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String aboutMyself = null;
        String address = null;

        id = entity.getId();
        aboutMyself = entity.getAboutMyself();
        address = entity.getAddress();

        PrivateDetailResponseDto privateDetailResponseDto = new PrivateDetailResponseDto( id, aboutMyself, address );

        return privateDetailResponseDto;
    }

    @Override
    public PublicDetailResponseDto toPublicDetailDto(DetailEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String aboutMyself = null;
        String address = null;

        aboutMyself = entity.getAboutMyself();
        address = entity.getAddress();

        PublicDetailResponseDto publicDetailResponseDto = new PublicDetailResponseDto( aboutMyself, address );

        return publicDetailResponseDto;
    }

    @Override
    public void updateEntityFromDto(DetailDto dto, DetailEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setAboutMyself( dto.aboutMyself() );
        entity.setAddress( dto.address() );
    }
}
