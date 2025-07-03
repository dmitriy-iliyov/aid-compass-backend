package com.aidcompass.security.domain.user.repositories;


import com.aidcompass.security.domain.user.models.UnconfirmedUserEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedUserRepository extends KeyValueRepository<UnconfirmedUserEntity, String> {

    boolean existsByEmail(String email);
}
