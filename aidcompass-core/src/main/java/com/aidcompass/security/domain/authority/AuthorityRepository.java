package com.aidcompass.security.domain.authority;

import com.aidcompass.security.domain.authority.models.AuthorityEntity;
import com.aidcompass.security.domain.authority.models.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

    Optional<AuthorityEntity> findByAuthority(Authority authority);

}
