package com.aidcompass.contact.models.dto.markers;

import com.aidcompass.contact_type.models.ContactType;

public interface CreateDto {
    ContactType type();
    String contact();
}
