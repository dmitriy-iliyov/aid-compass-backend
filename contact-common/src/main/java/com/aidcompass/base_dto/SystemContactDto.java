package com.aidcompass.base_dto;


import com.aidcompass.enums.ContactType;
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
