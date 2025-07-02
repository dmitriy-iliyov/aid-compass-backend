package com.aidcompass.customer;

import com.aidcompass.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.general.interfaces.PersistFacade;
import com.aidcompass.customer.models.CustomerDto;

import java.util.UUID;

public interface CustomerPersistFacade extends PersistFacade<CustomerDto, PrivateCustomerResponseDto> {
    void save(UUID id);
}
