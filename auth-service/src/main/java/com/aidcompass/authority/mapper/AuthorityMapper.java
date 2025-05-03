package com.aidcompass.authority.mapper;


import com.aidcompass.authority.models.Authority;
import com.aidcompass.authority.models.AuthorityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorityMapper {

    @Named("toAuthorityList")
    List<Authority> toAuthorityList(List<AuthorityEntity> authorityEntities);

    default Authority toAuthority(AuthorityEntity authorityEntity) {
        return authorityEntity != null ? authorityEntity.getAuthority() : null;
    }
}
