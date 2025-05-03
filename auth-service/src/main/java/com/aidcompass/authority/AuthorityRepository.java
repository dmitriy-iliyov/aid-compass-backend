package com.aidcompass.authority;

import com.aidcompass.authority.models.Authority;
import com.aidcompass.authority.models.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

    Optional<AuthorityEntity> findByAuthority(Authority authority);

}
