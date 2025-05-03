package com.aidcompass.customer;

import com.aidcompass.customer.mapper.CustomerMapper;
import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import com.aidcompass.exceptions.CustomerNotFoundByIdException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper;


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
        return customerMapper.toPrivateResponseDto(publicCustomerResponseDto);
    }

    @Transactional
    @Override
    public void updateById(UUID id, CustomerUpdateDto customerUpdateDto) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(CustomerNotFoundByIdException::new);
        customerMapper.updateEntityFromDto(customerUpdateDto, customerEntity);
        customerRepository.save(customerEntity);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByPassword(UUID id, String password) {
        customerRepository.deleteById(id);
    }
}
