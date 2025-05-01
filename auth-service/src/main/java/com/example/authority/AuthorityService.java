package com.example.authority;


import com.example.authority.models.Authority;
import com.example.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
