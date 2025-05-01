package com.example.contact.config;

import com.example.contact.services.SystemContactService;
import com.example.contact.validation.ContactPermissionValidator;
import com.example.contact.validation.ContactPermissionValidatorImpl;
import com.example.contact.validation.contact.ContactValidator;
import com.example.contact.validation.ownership.OwnershipValidator;
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
