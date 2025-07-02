package com.aidcompass.contact.core.models.dto.system;


import com.aidcompass.ContactType;
import lombok.Data;

import java.util.UUID;

@Data
public class SystemContactDto {
    private Long id;
    private UUID ownerId;
    private ContactType type;
    private String contact;
    private boolean isPrimary;
    private boolean isConfirmed;
    private boolean isLinkedToAccount;
}
