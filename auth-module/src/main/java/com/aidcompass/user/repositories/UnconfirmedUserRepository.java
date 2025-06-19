package com.aidcompass.user.repositories;


import com.aidcompass.user.models.UnconfirmedUserEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedUserRepository extends KeyValueRepository<UnconfirmedUserEntity, String> {

    boolean existsByEmail(String email);
}
