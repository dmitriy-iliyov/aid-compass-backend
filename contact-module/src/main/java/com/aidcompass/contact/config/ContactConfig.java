package com.aidcompass.contact.config;

import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.validators.*;
import com.aidcompass.contact.validation.validators.impl.PermissionValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Bean
    public PermissionValidator permissionValidator(SystemContactService service,
                                                   ContactOwnershipValidator contactOwnershipValidator,
                                                   ContactUniquenessValidator contactUniquenessValidator,
                                                   FormatValidator formatValidator) {
        return new PermissionValidatorImpl(service, contactOwnershipValidator, contactUniquenessValidator, formatValidator);
    }
}
