//package aidcompass.api.security.config;
//
//
//import aidcompass.api.security.models.token.factory.DefaultTokenFactory;
//import aidcompass.api.security.models.token.serializing.DefaultTokenDeserializer;
//import aidcompass.api.security.models.token.serializing.DefaultTokenSerializer;
//import aidcompass.api.security.core.interfaces.TokenDeserializer;
//import aidcompass.api.security.core.interfaces.TokenFactory;
//import aidcompass.api.security.core.interfaces.TokenSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TokenToolsConfig {
//
//    @Value("${aid.compass.jwt.secret}")
//    private String SECRET;
//
//
//    @Bean
//    public TokenSerializer tokenSerializer(){
//        return new DefaultTokenSerializer(SECRET);
//    }
//
//    @Bean
//    public TokenDeserializer tokenDeserializer() {
//        return new DefaultTokenDeserializer(SECRET);
//    }
//
//    @Bean
//    public TokenFactory tokenFactory() {
//        return new DefaultTokenFactory();
//    }
//
//}
