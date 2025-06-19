package com.aidcompass.contact.facades;

import com.aidcompass.contact.models.dto.ContactUpdateDto;

@FunctionalInterface
public interface ContactChangingListener {
    void callback(ContactUpdateDto contact);
}
