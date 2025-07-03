package com.aidcompass.contact.core.facades;

import com.aidcompass.contact.core.models.dto.ContactUpdateDto;

@FunctionalInterface
public interface ContactChangingListener {
    void callback(ContactUpdateDto contact);
}
