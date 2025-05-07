package com.aidcompass.contact.config;

import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.validators.*;
import com.aidcompass.contact.validation.validators.impl.PermissionValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Bean
    public PermissionValidator contactValidationFacade(SystemContactService service,
                                                       OwnershipValidator ownershipValidator,
                                                       UniquenessValidator uniquenessValidator,
                                                       FormatValidator formatValidator) {
        return new PermissionValidatorImpl(service, ownershipValidator, uniquenessValidator, formatValidator);
    }
}
