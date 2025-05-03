package com.aidcompass.contact_type;


import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.contact_type.models.ContactTypeDto;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.exceptions.not_found.ContentTypeNotFoundByTypeException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactTypeServiceImplIntegrationTests {

    @Autowired
    ContactTypeService contactTypeService;

    @Autowired
    ContactTypeRepository repository;

    @Container
    public static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:13")
                    .withDatabaseName("test_db")
                    .withUsername("testuser")
                    .withPassword("testpassword")
                    .withExposedPorts(5432);

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    @Order(1)
    @DisplayName("IT findByType() should return entity when exists")
    void findByType_shouldReturn_whenExists() {
        ContactTypeEntity entity = new ContactTypeEntity();
        entity.setType(ContactType.EMAIL);
        repository.save(entity);

        ContactTypeEntity found = contactTypeService.findByType(ContactType.EMAIL);

        assertThat(found.getType()).isEqualTo(ContactType.EMAIL);
    }

    @Test
    @Order(2)
    @DisplayName("IT findByType() should return entity when exists")
    void deleteByType_shouldThrowAfterDelete_whenExists() {
        ContactTypeEntity entity = repository.findByType(ContactType.EMAIL).orElse(null);
        if (entity != null)
            repository.deleteById(entity.getId());

        assertThatThrownBy(() -> contactTypeService.findByType(ContactType.PHONE_NUMBER))
                .isInstanceOf(ContentTypeNotFoundByTypeException.class);
    }

    @Test
    @Order(3)
    @DisplayName("IT findByType() should throw exception when not found")
    void findByType_whenPHONENUMBERNotExists_shouldThrow() {
        assertThatThrownBy(() -> contactTypeService.findByType(ContactType.PHONE_NUMBER))
                .isInstanceOf(ContentTypeNotFoundByTypeException.class);
    }

    @Test
    @Order(4)
    @DisplayName("IT saveAll() should persist and return mapped DTOs")
    void saveAll_shouldPersistAndReturnDtos() {
        List<ContactTypeDto> saved = contactTypeService.saveAll(List.of(ContactType.EMAIL, ContactType.PHONE_NUMBER));

        assertThat(saved).hasSize(2);
        assertThat(repository.findAll()).hasSize(2);
        assertThat(saved).extracting("type").containsExactlyInAnyOrder(ContactType.EMAIL, ContactType.PHONE_NUMBER);
    }
}
