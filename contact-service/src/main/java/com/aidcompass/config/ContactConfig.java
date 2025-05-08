package com.aidcompass.config;

import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.validators.*;
import com.aidcompass.contact.validation.validators.impl.PermissionValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Bean
    //@DependsOn("uniquenessValidator")
    public PermissionValidator permissionValidator(SystemContactService service,
                                                   OwnershipValidator ownershipValidator,
                                                   UniquenessValidator uniquenessValidator,
                                                   FormatValidator formatValidator) {
        return new PermissionValidatorImpl(service, ownershipValidator, uniquenessValidator, formatValidator);
    }

//    @Bean
//    public UniquenessValidator uniquenessValidator(SystemContactFacade facade) {
//        return new UniquenessValidatorImpl(facade);
//    }

//    @Bean
//    public CountValidator countValidator(SystemContactFacade facade) {
//        return new CountValidatorImpl(facade);
//    }
}
