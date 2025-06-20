package com.aidcompass;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class GlobalRedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    public static final String APPOINTMENT_DURATION_CACHE_NAME = "appointment:duration";
    public static final String APPOINTMENT_DURATION_MAP_CACHE_NAME = "appointment:duration:map";
    public static final String APPOINTMENTS_BY_FILTER_CACHE_NAME = "appointments:filter";
    public final static String CONF_TOKEN_KEY_TEMPLATE = "tkn:conf:";


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY
        );

        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.
                        SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(mapper)));
    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory, RedisCacheConfiguration config) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .disableCreateOnMissingCache()
                .withCacheConfiguration("doctors:public", config)
                .withCacheConfiguration("doctors:public:full", config)
                .withCacheConfiguration("doctors:spec", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:name", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:gender", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:approve", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:count", config.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("jurists:public", config)
                .withCacheConfiguration("jurists:public:full", config)
                .withCacheConfiguration("jurists:spec", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:name", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:gender", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:approve", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:count", config.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("customers:private", config)
                .withCacheConfiguration("customers:count", config.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("contact_types", config)
                .withCacheConfiguration("exists", config.entryTtl(Duration.ofSeconds(120)))
                .withCacheConfiguration("public_contacts", config.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("primary_contacts", config.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("contacts:progress", config.entryTtl(Duration.ofSeconds(3600)))

                .withCacheConfiguration(APPOINTMENT_DURATION_CACHE_NAME, config)
                .withCacheConfiguration(APPOINTMENT_DURATION_MAP_CACHE_NAME, config.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration(APPOINTMENTS_BY_FILTER_CACHE_NAME, config.entryTtl(Duration.ofSeconds(500)))
                .withCacheConfiguration("day", config)
                .withCacheConfiguration("day:full", config)
                .withCacheConfiguration("weak", config)
                .withCacheConfiguration("month", config)

                .withCacheConfiguration("avatars:url", config)
                .withCacheConfiguration("avatars:url:map", config.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("avatars:sas_link", config)
                .withCacheConfiguration("avatars:sas_link:map", config.entryTtl(Duration.ofSeconds(60)))
                .build();
    }
}