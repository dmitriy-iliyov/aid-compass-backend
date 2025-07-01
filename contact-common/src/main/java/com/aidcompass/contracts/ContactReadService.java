package com.aidcompass.contracts;


import com.aidcompass.dto.PrivateContactResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;

import java.util.List;
import java.util.UUID;

public interface ContactReadService {
    List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId);

    List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId);
}
