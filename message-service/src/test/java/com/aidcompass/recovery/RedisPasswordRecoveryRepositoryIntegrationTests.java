package com.aidcompass.recovery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class RedisPasswordRecoveryRepositoryIntegrationTests {

    @Autowired
    RedisPasswordRecoveryRepository redisPasswordRecoveryRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private final String RECOVERY_TOKEN_KEY_TEMPLATE = "tkn:pass:recov:%s";

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7.0.5")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Test
    @DisplayName("IT: save() should store token")
    void save_shouldSaveToRedis() {
        UUID token = UUID.randomUUID();
        String email = "recover@test.com";

        redisPasswordRecoveryRepository.save(token, email);

        assertTrue(Boolean.TRUE.equals(
                redisTemplate.hasKey(RECOVERY_TOKEN_KEY_TEMPLATE.formatted(token))
        ));
    }

    @Test
    @DisplayName("IT: findAndDeleteByToken() should retrieve and remove token")
    void findAndDeleteByToken__whenExist_shouldReturnEmail() {
        UUID token = UUID.randomUUID();
        String email = "recover@test.com";

        redisPasswordRecoveryRepository.save(token, email);

        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(token);
        assertTrue(result.isPresent());
        assertEquals(email, result.get());

        result = redisPasswordRecoveryRepository.findAndDeleteByToken(token);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("IT: save() should store token with TTL")
    void save_shouldStoreWithTTL() {
        UUID token = UUID.randomUUID();
        String email = "recover@test.com";

        redisPasswordRecoveryRepository.save(token, email);

        Long ttl = redisTemplate.getExpire(RECOVERY_TOKEN_KEY_TEMPLATE.formatted(token), TimeUnit.SECONDS);
        assertNotNull(ttl);
        assertTrue(ttl > 0, "TTL must be greater than 0");
    }

    @Test
    @DisplayName("IT: findAndDeleteByToken() should return empty if token not exists")
    void findAndDeleteByToken_whenNotExist_shouldReturnEmpty() {
        UUID token = UUID.randomUUID();

        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(token);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("IT: Token should be removed after first access")
    void tokenShouldBeRemovedAfterFirstAccess() {
        UUID token = UUID.randomUUID();
        String email = "recover@test.com";

        redisPasswordRecoveryRepository.save(token, email);

        Optional<String> first = redisPasswordRecoveryRepository.findAndDeleteByToken(token);
        Optional<String> second = redisPasswordRecoveryRepository.findAndDeleteByToken(token);

        assertTrue(first.isPresent());
        assertEquals(email, first.get());
        assertTrue(second.isEmpty());
    }

    @Test
    @DisplayName("IT: Key should match expected naming convention")
    void keyShouldMatchNamingConvention() {
        UUID token = UUID.randomUUID();
        String email = "recover@test.com";

        redisPasswordRecoveryRepository.save(token, email);

        String expectedKey = "tkn:pass:recov:" + token;
        assertTrue(Boolean.TRUE.equals(redisTemplate.hasKey(expectedKey)));
    }
}