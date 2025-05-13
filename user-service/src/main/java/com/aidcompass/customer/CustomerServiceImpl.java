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
import com.aidcompass.utils.uuid.UuidFactory;
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
    public void save(UUID userId) {
        ProfileStatusEntity statusEntity = statusService.findByStatus(ProfileStatus.INCOMPLETE);
        customerRepository.save(new CustomerEntity(userId, statusEntity));
    }

    @Transactional
    @Override
    public PrivateCustomerResponseDto save(UUID userId, CustomerRegistrationDto customerRegistrationDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(userId, customerRegistrationDto);
        ProfileStatusEntity statusEntity = statusService.findByStatus(ProfileStatus.COMPLETE);
        customerEntity.setProfileStatus(statusEntity);
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
    public PrivateCustomerResponseDto findPrivateById(UUID userId) {
        CustomerEntity entity = customerRepository.findWithStatusEntityByUserId(userId).orElseThrow(CustomerNotFoundByUserIdException::new);
        return customerMapper.toPrivateResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateById(UUID userId, CustomerUpdateDto customerUpdateDto) {
        CustomerEntity customerEntity = customerRepository.findWithStatusEntityByUserId(userId).orElseThrow(CustomerNotFoundByIdException::new);
        customerMapper.updateEntityFromDto(customerUpdateDto, customerEntity);
        customerRepository.save(customerEntity);
    }

    @Transactional
    @Override
    public void deleteById(UUID userId) {
        customerRepository.deleteByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteByPassword(UUID userId, String password) {
        customerRepository.deleteByUserId(userId);
    }
}
