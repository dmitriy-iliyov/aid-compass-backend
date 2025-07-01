package com.aidcompass.customer;

import com.aidcompass.general.interfaces.PersistFacade;
import com.aidcompass.customer.models.CustomerDto;
import com.aidcompass.customer.dto.PrivateCustomerResponseDto;

import java.util.UUID;

public interface CustomerPersistFacade extends PersistFacade<CustomerDto, PrivateCustomerResponseDto> {
    void save(UUID id);
}
