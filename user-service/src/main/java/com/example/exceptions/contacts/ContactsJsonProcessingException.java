package com.example.exceptions.contacts;

import com.example.global_exceptions.Exception;

public class ContactsJsonProcessingException extends Exception {

    public ContactsJsonProcessingException() {
        super("Contacts parse exception!");
    }
}
