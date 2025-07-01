package com.aidcompass.customer.services;

import com.aidcompass.customer.CustomerDeleteService;
import com.aidcompass.customer.CustomerReadService;
import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.models.PublicCustomerResponseDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerService extends CustomerPersistService, CustomerReadService, CustomerDeleteService {

    PublicCustomerResponseDto findPublicById(UUID id);

    List<PublicCustomerResponseDto> findAllByIds(Set<UUID> ids);

    PrivateCustomerResponseDto updateById(UUID userId, CustomerDto dto);

    long count();
}
