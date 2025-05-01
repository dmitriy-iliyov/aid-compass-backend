package com.example.customer;

import com.example.contact.models.dto.ContactsCreateDto;
import com.example.customer.models.dto.CustomerRegistrationDto;
import com.example.customer.models.dto.CustomerUpdateDto;
import com.example.customer.models.dto.PrivateCustomerResponseDto;
import com.example.customer.models.dto.PublicCustomerResponseDto;

import java.util.UUID;

public interface CustomerService {
    void save(UUID id, CustomerRegistrationDto customerRegistrationDto);

    PublicCustomerResponseDto findPublicById(UUID id);

    PrivateCustomerResponseDto findPrivateById(UUID id);

    void updateById(UUID id, CustomerUpdateDto customerUpdateDto);

    void updateContactsById(UUID id, ContactsCreateDto contactsCreateDto);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
