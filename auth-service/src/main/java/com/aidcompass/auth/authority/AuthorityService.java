package com.aidcompass.auth.authority;


import com.aidcompass.auth.authority.models.Authority;
import com.aidcompass.auth.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
