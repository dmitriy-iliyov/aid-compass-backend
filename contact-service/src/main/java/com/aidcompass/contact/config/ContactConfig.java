package com.aidcompass.contact.config;

import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.ContactPermissionValidator;
import com.aidcompass.contact.validation.ContactPermissionValidatorImpl;
import com.aidcompass.contact.validation.contact.ContactValidator;
import com.aidcompass.contact.validation.ownership.OwnershipValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactConfig {

    @Bean
    public ContactPermissionValidator contactValidationFacade(SystemContactService service,
                                                              ContactValidator contactValidator,
                                                              OwnershipValidator ownershipValidator) {
        return new ContactPermissionValidatorImpl(service, contactValidator, ownershipValidator);
    }
}
