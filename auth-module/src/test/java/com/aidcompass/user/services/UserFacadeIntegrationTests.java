//package com.aidcompass.user.services;
//
//import com.aidcompass.user.repositories.UnconfirmedUserRepository;
//import com.aidcompass.user.repositories.UserRepository;
//import com.aidcompass.user.models.dto.UserRegistrationDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Testcontainers
//public class UserFacadeIntegrationTests {
//
//    @Autowired
//    UnconfirmedUserRepository unconfirmedUserRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserFacadeImpl userFacade;
//
//    @Container
//    static GenericContainer<?> redis = new GenericContainer<>("redis:7.0.5")
//            .withExposedPorts(6379);
//
//    @DynamicPropertySource
//    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", redis::getHost);
//        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
//    }
//
//    @Test
//    void save() {
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("test@gmail.com", "test_password");
//        userFacade.save(userRegistrationDto);
//
//        assertTrue(unconfirmedUserRepository.existsById("test@gmail.com"));
//    }
//
//}
