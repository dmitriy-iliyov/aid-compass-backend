package com.example.exceptions.contacts;

import com.aidcompass.common.global_exceptions.Exception;

public class ContactsJsonProcessingException extends Exception {

    public ContactsJsonProcessingException() {
        super("Contacts parse exception!");
    }
}
