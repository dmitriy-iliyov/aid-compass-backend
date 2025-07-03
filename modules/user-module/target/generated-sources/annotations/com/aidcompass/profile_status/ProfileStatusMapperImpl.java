package com.aidcompass.profile_status;

import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
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
public class ProfileStatusMapperImpl implements ProfileStatusMapper {

    @Override
    public ProfileStatusEntity toEntity(ProfileStatus status) {
        if ( status == null ) {
            return null;
        }

        ProfileStatusEntity profileStatusEntity = new ProfileStatusEntity();

        profileStatusEntity.setProfileStatus( status );

        return profileStatusEntity;
    }

    @Override
    public List<ProfileStatusEntity> toEntityList(List<ProfileStatus> statusList) {
        if ( statusList == null ) {
            return null;
        }

        List<ProfileStatusEntity> list = new ArrayList<ProfileStatusEntity>( statusList.size() );
        for ( ProfileStatus profileStatus : statusList ) {
            list.add( toEntity( profileStatus ) );
        }

        return list;
    }
}
