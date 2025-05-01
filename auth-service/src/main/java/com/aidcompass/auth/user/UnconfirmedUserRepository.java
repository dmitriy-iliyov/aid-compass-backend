package com.aidcompass.auth.user;


import com.example.user.models.entity.UnconfirmedUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnconfirmedUserRepository extends CrudRepository<UnconfirmedUserEntity, String> {

    boolean existsByEmail(String email);
}
