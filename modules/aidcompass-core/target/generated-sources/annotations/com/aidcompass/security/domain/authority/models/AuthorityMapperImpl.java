package com.aidcompass.security.domain.authority.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public List<Authority> toAuthorityList(List<AuthorityEntity> authorityEntities) {
        if ( authorityEntities == null ) {
            return null;
        }

        List<Authority> list = new ArrayList<Authority>( authorityEntities.size() );
        for ( AuthorityEntity authorityEntity : authorityEntities ) {
            list.add( toAuthority( authorityEntity ) );
        }

        return list;
    }
}
