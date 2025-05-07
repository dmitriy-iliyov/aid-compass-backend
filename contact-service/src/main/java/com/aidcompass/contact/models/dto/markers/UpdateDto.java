package com.aidcompass.contact.models.dto.markers;

import com.aidcompass.contact_type.models.ContactType;

public interface UpdateDto {
    Long id();
    ContactType type();
    String contact();
    boolean isPrimary();
}
