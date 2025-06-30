package com.aidcompass.customer.services;

import com.aidcompass.customer.models.dto.CustomerDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerService extends PersistCustomerService {

    PublicCustomerResponseDto findPublicById(UUID id);

    PrivateCustomerResponseDto findPrivateById(UUID id);

    List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids);

    PrivateCustomerResponseDto updateById(UUID userId, CustomerDto dto);

    void deleteById(UUID id);

    long count();
}
