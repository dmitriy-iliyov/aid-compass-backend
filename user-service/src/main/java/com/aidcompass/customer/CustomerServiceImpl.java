package com.aidcompass.customer;

import com.aidcompass.profile_status.ProfileStatusService;
import com.aidcompass.profile_status.models.ProfileStatus;
import com.aidcompass.customer.mapper.CustomerMapper;
import com.aidcompass.customer.models.CustomerEntity;
import com.aidcompass.customer.models.dto.CustomerRegistrationDto;
import com.aidcompass.customer.models.dto.CustomerUpdateDto;
import com.aidcompass.customer.models.dto.PrivateCustomerResponseDto;
import com.aidcompass.customer.models.dto.PublicCustomerResponseDto;
import com.aidcompass.exceptions.customer.CustomerNotFoundByIdException;
import com.aidcompass.exceptions.customer.CustomerNotFoundByUserIdException;
import com.aidcompass.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ProfileStatusService statusService;


    @Transactional
    @Override
    public void save(UUID id) {
        ProfileStatusEntity statusEntity = statusService.findByStatus(ProfileStatus.INCOMPLETE);
        customerRepository.save(new CustomerEntity(id, statusEntity));
    }

    @Transactional
    @Override
    public PrivateCustomerResponseDto save(UUID id, CustomerRegistrationDto customerRegistrationDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(id, customerRegistrationDto);
        ProfileStatusEntity statusEntity = statusService.findByStatus(ProfileStatus.COMPLETE);
        customerEntity.setProfileStatusEntity(statusEntity);
        return customerMapper.toPrivateResponseDto(customerRepository.save(customerEntity));
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
        CustomerEntity entity = customerRepository.findById(id).orElseThrow(CustomerNotFoundByUserIdException::new);
        return customerMapper.toPrivateResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateById(UUID userId, CustomerUpdateDto customerUpdateDto) {
        CustomerEntity customerEntity = customerRepository.findById(userId).orElseThrow(CustomerNotFoundByIdException::new);
        customerMapper.updateEntityFromDto(customerUpdateDto, customerEntity);
        customerRepository.save(customerEntity);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }
}
