package com.example.exceptions.contacts;

import com.example.exceptions.Exception;

public class ContactsJsonProcessingException extends Exception {

    public ContactsJsonProcessingException() {
        super("Contacts parse exception!");
    }
}
