package com.aidcompass.customer.services;

import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.dto.PrivateCustomerResponseDto;
import com.aidcompass.detail.models.DetailEntity;

import java.util.UUID;

public interface CustomerPersistService extends PersistService<CustomerDto, PrivateCustomerResponseDto> {
    void save(UUID id, DetailEntity detail);
}
