package com.aidcompass.jurist.mapper;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.PrivateDetailResponseDto;
import com.aidcompass.detail.models.PublicDetailResponseDto;
import com.aidcompass.jurist.models.JuristEntity;
import com.aidcompass.jurist.models.dto.FullPrivateJuristResponseDto;
import com.aidcompass.jurist.models.dto.FullPublicJuristResponseDto;
import com.aidcompass.jurist.models.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class FullJuristMapperImpl implements FullJuristMapper {

    @Autowired
    private JuristMapper juristMapper;

    @Override
    public FullPrivateJuristResponseDto toFullPrivateDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PrivateDetailResponseDto detail = null;

        detail = detailEntityToPrivateDetailResponseDto( entity.getDetailEntity() );

        PrivateJuristResponseDto jurist = juristMapper.toPrivateDto(entity);

        FullPrivateJuristResponseDto fullPrivateJuristResponseDto = new FullPrivateJuristResponseDto( jurist, detail );

        return fullPrivateJuristResponseDto;
    }

    @Override
    public FullPrivateJuristResponseDto toFullPrivateDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity) {
        if ( paramSpecializations == null && entity == null ) {
            return null;
        }

        PrivateDetailResponseDto detail = null;
        if ( entity != null ) {
            detail = detailEntityToPrivateDetailResponseDto( entity.getDetailEntity() );
        }

        PrivateJuristResponseDto jurist = juristMapper.toPrivateDto(paramSpecializations, entity);

        FullPrivateJuristResponseDto fullPrivateJuristResponseDto = new FullPrivateJuristResponseDto( jurist, detail );

        return fullPrivateJuristResponseDto;
    }

    @Override
    public FullPublicJuristResponseDto toFullPublicDto(JuristEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PublicJuristResponseDto jurist = null;
        PublicDetailResponseDto detail = null;

        jurist = juristMapper.toPublicDto( entity );
        detail = detailEntityToPublicDetailResponseDto( entity.getDetailEntity() );

        FullPublicJuristResponseDto fullPublicJuristResponseDto = new FullPublicJuristResponseDto( jurist, detail );

        return fullPublicJuristResponseDto;
    }

    protected PrivateDetailResponseDto detailEntityToPrivateDetailResponseDto(DetailEntity detailEntity) {
        if ( detailEntity == null ) {
            return null;
        }

        Long id = null;
        String aboutMyself = null;
        String address = null;

        id = detailEntity.getId();
        aboutMyself = detailEntity.getAboutMyself();
        address = detailEntity.getAddress();

        PrivateDetailResponseDto privateDetailResponseDto = new PrivateDetailResponseDto( id, aboutMyself, address );

        return privateDetailResponseDto;
    }

    protected PublicDetailResponseDto detailEntityToPublicDetailResponseDto(DetailEntity detailEntity) {
        if ( detailEntity == null ) {
            return null;
        }

        String aboutMyself = null;
        String address = null;

        aboutMyself = detailEntity.getAboutMyself();
        address = detailEntity.getAddress();

        PublicDetailResponseDto publicDetailResponseDto = new PublicDetailResponseDto( aboutMyself, address );

        return publicDetailResponseDto;
    }
}
