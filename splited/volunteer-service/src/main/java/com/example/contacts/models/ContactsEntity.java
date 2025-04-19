package com.example.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsEntity {

    private Map<ContactType, Map<Integer, String>> contacts;


    public void addEmail(String email) {
        if (!contacts.containsKey(ContactType.EMAIL)) {
            contacts.put(ContactType.EMAIL, new HashMap<>());
        }
        Map<Integer, String> emails = contacts.get(ContactType.EMAIL);
        if (emails.size() < 2) {
            emails.put(emails.size() + 1, email);
        } else {
            throw new IllegalStateException("");
        }
    }

    public void addPhoneNumber(String phoneNumber) {
        if (!contacts.containsKey(ContactType.PHONE_NUMBER)) {
            contacts.put(ContactType.PHONE_NUMBER, new HashMap<>());
        }
        Map<Integer, String> phones = contacts.get(ContactType.PHONE_NUMBER);
        if(phones.size() < 2) {
            phones.put(phones.size() + 1, phoneNumber);
        } else {

        }
    }
}
