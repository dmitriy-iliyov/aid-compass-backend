package com.aidcompass.customer;

import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerService {
    void save(UUID userId);

    PrivateCustomerResponseDto save(UUID userId, CustomerRegistrationDto customerRegistrationDto);

    PublicCustomerResponseDto findPublicById(UUID id);

    PrivateCustomerResponseDto findPrivateById(UUID userId);

    void updateById(UUID userId, CustomerUpdateDto customerUpdateDto);

    void deleteById(UUID userId);

    void deleteByPassword(UUID userId, String password);
}
