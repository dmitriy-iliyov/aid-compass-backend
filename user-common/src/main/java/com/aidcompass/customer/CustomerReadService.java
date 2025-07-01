package com.aidcompass.customer;

import com.aidcompass.customer.dto.PrivateCustomerResponseDto;

import java.util.UUID;

public interface CustomerReadService {
    PrivateCustomerResponseDto findPrivateById(UUID id);
}
