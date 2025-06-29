package com.aidcompass.service;

import com.aidcompass.enums.Authority;
import com.aidcompass.security.core.models.authority.AuthorityRepository;
import com.aidcompass.security.core.models.authority.AuthorityService;
import com.aidcompass.security.core.models.authority.models.AuthorityEntity;
import com.aidcompass.uuid.UuidFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class ServiceDatabaseInitializer {

    private final ServiceRepository repository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void setUp() {
        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_SCHEDULE_TASK_SERVICE);
        repository.save(new ServiceEntity(
                UuidFactory.generate(),
                "service-name",
                passwordEncoder.encode("service-key"),
                authorityEntity,
                Instant.now(),
                Instant.now(),
                false)
        );
    }
}