package com.aidcompass.customer;

import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.dto.PublicCustomerResponseDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerReadService {
    PrivateCustomerResponseDto findPrivateById(UUID id);

    PublicCustomerResponseDto findPublicById(UUID id);

    List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids);
}
