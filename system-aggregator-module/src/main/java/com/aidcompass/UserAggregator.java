package com.aidcompass;

import com.aidcompass.dto.PublicContactResponseDto;
import com.aidcompass.dto.UserDto;
import com.aidcompass.models.UserType;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface UserAggregator {
    UserDto findUserByIdAndType(UUID id, UserType type);

    Map<UUID, UserDto> findAllCustomerByIdIn(Set<UUID> ids);

    PublicContactResponseDto findPrimaryContactByOwnerId(UUID id);
}
