package com.aidcompass.security.configs;

import com.aidcompass.security.core.models.token.factory.ServiceTokenFactory;
import com.aidcompass.security.core.models.token.factory.TokenFactory;
import com.aidcompass.security.core.models.token.factory.UserTokenFactory;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializer;
import com.aidcompass.security.core.models.token.serializing.TokenDeserializerImpl;
import com.aidcompass.security.core.models.token.serializing.TokenSerializer;
import com.aidcompass.security.core.models.token.serializing.TokenSerializerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenToolsConfig {

    @Value("${auth.token.secret}")
    private String SECRET;


    @Bean
    public TokenSerializer tokenSerializer(){
        return new TokenSerializerImpl(SECRET);
    }

    @Bean
    public TokenDeserializer tokenDeserializer() {
        return new TokenDeserializerImpl(SECRET);
    }

    @Bean(name = "userTokenFactory")
    public TokenFactory userTokenFactory() {
        return new UserTokenFactory();
    }

    @Bean(name = "serviceTokenFactory")
    public TokenFactory serviceTokenFactory() {
        return new ServiceTokenFactory();
    }

}