package com.aidcompass.customer;

import com.aidcompass.customer.models.dto.CustomerDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface PersistCustomerFacade {
    void save(UUID id);

    PrivateCustomerResponseDto save(UUID id, CustomerDto dto, HttpServletResponse response);
}
