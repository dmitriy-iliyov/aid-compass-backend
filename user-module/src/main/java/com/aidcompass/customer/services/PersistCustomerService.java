package com.aidcompass.customer.services;

import com.aidcompass.customer.models.dto.CustomerDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.detail.models.DetailEntity;

import java.util.UUID;

public interface PersistCustomerService {
    void save(UUID id, DetailEntity detail);

    PrivateCustomerResponseDto save(UUID id, DetailEntity detailEntity, CustomerDto dto);
}
