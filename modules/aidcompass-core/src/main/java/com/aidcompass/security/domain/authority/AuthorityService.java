package com.aidcompass.security.domain.authority;


import com.aidcompass.security.domain.authority.models.AuthorityEntity;
import com.aidcompass.security.domain.authority.models.Authority;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
