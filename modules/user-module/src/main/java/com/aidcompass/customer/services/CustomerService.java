package com.aidcompass.customer.services;

import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.PublicCustomerResponseDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerService extends CustomerPersistService {

    long count();

    PrivateCustomerResponseDto findPrivateById(UUID id);

    PublicCustomerResponseDto findPublicById(UUID id);

    List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids);

    PrivateCustomerResponseDto updateById(UUID userId, CustomerDto dto);

    void deleteById(UUID id);
}
