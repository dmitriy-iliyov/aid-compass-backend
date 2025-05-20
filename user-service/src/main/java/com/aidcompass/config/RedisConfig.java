package com.aidcompass.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private Integer port;

    private long DOCTOR_PUBLIC_MIN_TTL = 300;
    private long DOCTOR_FULL_PUBLIC_MIN_TTL = 1;
    private long DOCTOR_SPECS_SECS_TTL = 120;
    private long DOCTOR_NAMES_SECS_TTL = 300;
    private long DOCTOR_UNAPRV_SECS_TTL = 300;
    private long DOCTOR_APRV_SECS_TTL = 120;


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("doctor:public", config)
                .withCacheConfiguration("doctor:public:full", config)
                .withCacheConfiguration("doctor:specs", config.entryTtl(Duration.ofSeconds(DOCTOR_SPECS_SECS_TTL)))
                .withCacheConfiguration("doctor:name", config.entryTtl(Duration.ofSeconds(DOCTOR_NAMES_SECS_TTL)))
                .withCacheConfiguration("doctor:aprv", config.entryTtl(Duration.ofSeconds(DOCTOR_APRV_SECS_TTL)))
                .withCacheConfiguration("doctor:unprv", config.entryTtl(Duration.ofSeconds(DOCTOR_UNAPRV_SECS_TTL)))
                .withCacheConfiguration("doctor:spec", config)
                .withCacheConfiguration("profile:profileStatus", config)
                .build();
    }
}
