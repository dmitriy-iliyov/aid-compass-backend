package com.aidcompass;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
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
    public static final String APPOINTMENTS_CACHE_NAME = "appointments";
    public final static String CONF_TOKEN_KEY_TEMPLATE = "tkn:conf:";
    public static final String LIST_OF_TIMES_CACHE_NAME = "lost_of_times:public";
    public static final String PRIVATE_LIST_OF_TIMES_CACHE_NAME = "lost_of_times:private";
    public static final String MONTH_DATES_CACHE_NAME = "month:dates";
    public static final String PRIVATE_MONTH_DATES_CACHE_NAME = "month:dates:private";


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {

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
    public RedisCacheConfiguration withCacheNullValuesRedisConfiguration() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY
        );

        return RedisCacheConfiguration.defaultCacheConfig()
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
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory,
                                     @Qualifier("defaultRedisCacheConfiguration") RedisCacheConfiguration defaultConfig,
                                     @Qualifier("withCacheNullValuesRedisConfiguration") RedisCacheConfiguration withCacheNullValuesRedisConfiguration) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .disableCreateOnMissingCache()
                .withCacheConfiguration("doctors:public", defaultConfig)
                .withCacheConfiguration("doctors:public:full", defaultConfig)
                .withCacheConfiguration("doctors:spec", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:name", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:gender", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:approve", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("doctors:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("jurists:public", defaultConfig)
                .withCacheConfiguration("jurists:public:full", defaultConfig)
                .withCacheConfiguration("jurists:spec", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:name", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:gender", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:approve", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .withCacheConfiguration("jurists:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("customers:private", defaultConfig)
                .withCacheConfiguration("customers:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("contact_types", defaultConfig)
                .withCacheConfiguration("exists", defaultConfig.entryTtl(Duration.ofSeconds(120)))
                .withCacheConfiguration("public_contacts", defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("primary_contacts", defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("contacts:progress", defaultConfig.entryTtl(Duration.ofSeconds(3600)))

                .withCacheConfiguration(APPOINTMENT_DURATION_CACHE_NAME, defaultConfig)
                .withCacheConfiguration(APPOINTMENT_DURATION_MAP_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration(APPOINTMENTS_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration(LIST_OF_TIMES_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(PRIVATE_LIST_OF_TIMES_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(MONTH_DATES_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(PRIVATE_MONTH_DATES_CACHE_NAME, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("day", defaultConfig)
                .withCacheConfiguration("day:full", defaultConfig)
                .withCacheConfiguration("weak", defaultConfig)
                .withCacheConfiguration("month", defaultConfig)

                .withCacheConfiguration("avatars:url:map", defaultConfig.entryTtl(Duration.ofSeconds(60)))
                .build();
    }
}