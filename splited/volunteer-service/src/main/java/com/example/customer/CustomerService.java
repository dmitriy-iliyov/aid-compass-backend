package com.example.customer;

import com.example.contacts.models.ContactsDto;
import com.example.customer.models.dto.CustomerRegistrationDto;
import com.example.customer.models.dto.CustomerUpdateDto;
import com.example.customer.models.dto.PrivateCustomerResponseDto;
import com.example.customer.models.dto.PublicCustomerResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerService {
    void save(UUID id, CustomerRegistrationDto customerRegistrationDto);

    PublicCustomerResponseDto findPublicById(UUID id);

    PrivateCustomerResponseDto findPrivateById(UUID id);

    void updateById(UUID id, CustomerUpdateDto customerUpdateDto);

    @Transactional
    void updateContactsById(UUID id, ContactsDto contactsDto);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
