package com.aidcompass.system;

import com.aidcompass.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.information.dto.UserDto;
import com.aidcompass.system.models.UserType;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface UserAggregator {
    UserDto findUserByIdAndType(UUID id, UserType type);

    Map<UUID, UserDto> findAllCustomerByIdIn(Set<UUID> ids);

    PublicContactResponseDto findPrimaryContactByOwnerId(UUID id);
}
