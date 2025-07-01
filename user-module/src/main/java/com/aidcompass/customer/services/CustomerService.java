package com.aidcompass.customer.services;

import com.aidcompass.customer.CustomerDeleteService;
import com.aidcompass.customer.CustomerReadService;
import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.CustomerDto;

import java.util.UUID;

public interface CustomerService extends CustomerPersistService, CustomerReadService, CustomerDeleteService {

    PrivateCustomerResponseDto updateById(UUID userId, CustomerDto dto);

    long count();
}
