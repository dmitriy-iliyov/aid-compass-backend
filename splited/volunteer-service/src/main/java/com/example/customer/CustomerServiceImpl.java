package com.example.customer;

import com.example.contacts.mapper.ContactsMapper;
import com.example.contacts.models.ContactsDto;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.models.CustomerEntity;
import com.example.customer.models.dto.CustomerRegistrationDto;
import com.example.customer.models.dto.CustomerUpdateDto;
import com.example.customer.models.dto.PrivateCustomerResponseDto;
import com.example.customer.models.dto.PublicCustomerResponseDto;
import com.example.exceptions.CustomerNotFoundByIdException;
import com.example.exceptions.contacts.ContactsJsonProcessingException;
import com.example.rest_clients.avatar.AvatarService;
import com.example.rest_clients.user.UserService;
import com.example.rest_clients.user.dto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ContactsMapper contactsMapper;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final AvatarService avatarService;


    @Transactional
    @Override
    public void save(UUID id, CustomerRegistrationDto customerRegistrationDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(id, customerRegistrationDto);
        customerRepository.save(customerEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PublicCustomerResponseDto findPublicById(UUID id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(
                CustomerNotFoundByIdException::new
        );
        return customerMapper.toPublicResponseDto(customerEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateCustomerResponseDto findPrivateById(UUID id) {
        PublicCustomerResponseDto publicCustomerResponseDto = customerMapper.toPublicResponseDto(
                customerRepository.findById(id).orElseThrow(CustomerNotFoundByIdException::new)
        );
        UserResponseDto userResponseDto = userService.findById(id);
        return customerMapper.toPrivateResponseDto(publicCustomerResponseDto, userResponseDto);
    }

    @Transactional
    @Override
    public void updateById(UUID id, CustomerUpdateDto customerUpdateDto) {
        userService.update(id, customerUpdateDto.user());
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(CustomerNotFoundByIdException::new);
        customerMapper.updateEntityFromDto(customerUpdateDto, customerEntity);
        customerRepository.save(customerEntity);
    }

    @Transactional
    @Override
    public void updateContactsById(UUID id, ContactsDto contactsDto) {
        try {
            String contactsJson = objectMapper.writeValueAsString(contactsMapper.toEntity(contactsDto));
            customerRepository.updateContactsById(id, contactsJson);
        } catch (JsonProcessingException e) {
            throw new ContactsJsonProcessingException();
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        userService.deleteById(id);
        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByPassword(UUID id, String password) {
        userService.deleteByPassword(id, password);
        customerRepository.deleteById(id);
    }
}
