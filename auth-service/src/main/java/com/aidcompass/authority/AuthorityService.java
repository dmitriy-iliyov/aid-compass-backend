package com.aidcompass.authority;


import com.aidcompass.authority.models.Authority;
import com.aidcompass.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
