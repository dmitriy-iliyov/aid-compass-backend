package com.example.security.core.models.token;

import com.example.clicker.security.core.models.token.models.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeactivateTokenRepository extends CrudRepository<TokenEntity, Long> {

    boolean existsById(UUID id);

}
