package com.aidcompass.security.core.models.authority;


import com.aidcompass.enums.Authority;
import com.aidcompass.security.core.models.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
