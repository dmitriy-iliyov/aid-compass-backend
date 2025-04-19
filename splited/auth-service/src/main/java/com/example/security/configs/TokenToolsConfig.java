package com.example.security.configs;


import com.example.clicker.security.core.models.token.factory.TokenFactory;
import com.example.clicker.security.core.models.token.factory.TokenFactoryImpl;
import com.example.clicker.security.core.models.token.serializing.TokenDeserializer;
import com.example.clicker.security.core.models.token.serializing.TokenDeserializerImpl;
import com.example.clicker.security.core.models.token.serializing.TokenSerializer;
import com.example.clicker.security.core.models.token.serializing.TokenSerializerImpl;
import com.example.clicker.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenToolsConfig {

    private final UserFacade userFacade;

    @Value("${clicker.api.auth.jwt.secret}")
    private String SECRET;


    @Bean
    public TokenSerializer tokenSerializer(){
        return new TokenSerializerImpl(SECRET);
    }

    @Bean
    public TokenDeserializer tokenDeserializer() {
        return new TokenDeserializerImpl(SECRET);
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new TokenFactoryImpl(userFacade);
    }

}
