package com.aidcompass.contact_type;

import com.aidcompass.contact_type.models.ContactType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class ContactTypeDatabaseInitializer {

    private final ContactTypeService contactTypeService;
    private final static List<ContactType> contactTypeList =
            new ArrayList<>(List.of(ContactType.EMAIL, ContactType.PHONE_NUMBER));


    @PostConstruct
    public void setListOfContactType() {
        try {
            contactTypeService.saveAll(contactTypeList);
        } catch (Exception e) {
            log.error("Error when add ContactType to database: {}", e.getMessage());
        }
    }
}
