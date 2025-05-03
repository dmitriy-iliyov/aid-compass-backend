package com.aidcompass.user.repositories;


import com.aidcompass.user.models.entity.UnconfirmedUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedUserRepository extends CrudRepository<UnconfirmedUserEntity, String> {

    boolean existsByEmail(String email);
}
