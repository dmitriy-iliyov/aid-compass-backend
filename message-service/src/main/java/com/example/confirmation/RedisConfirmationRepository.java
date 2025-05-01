package com.example.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Repository
@RequiredArgsConstructor
public class RedisConfirmationRepository implements ConfirmationRepository {

    @Value("${aid.compass.api.confirm.ttl.secs}")
    private Long TOKEN_TTL;

    private final String CONFIRMATION_TOKEN_KEY_TEMPLATE = "tkn:rsrc:conf:%s";
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(UUID token, String confResource) {
        String key = CONFIRMATION_TOKEN_KEY_TEMPLATE.formatted(token);
        redisTemplate.opsForValue().set(key, confResource, TOKEN_TTL, TimeUnit.SECONDS);
    }

    @Override
    public Optional<String> findAndDeleteByToken(UUID token) {
        String key = CONFIRMATION_TOKEN_KEY_TEMPLATE.formatted(token);
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(key));
    }
}
