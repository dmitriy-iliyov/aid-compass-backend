package com.aidcompass.contact.core.models.markers;


import com.aidcompass.ContactType;

public interface UpdateDto {
    Long id();
    ContactType type();
    String contact();
    boolean isPrimary();
}
