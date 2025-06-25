package com.aidcompass.service;

import com.aidcompass.exceptions.ServiceNotFoundByServiceNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUserDetailsService implements UserDetailsService {

    private final ServiceRepository repository;


    @Override
    public UserDetails loadUserByUsername(String serviceName) throws UsernameNotFoundException {
        System.out.println(serviceName);
        ServiceEntity entity = repository.findByServiceName(serviceName).orElseThrow(
                ServiceNotFoundByServiceNameException::new
        );
        return new ServiceUserDetails(
                entity.getId(),
                entity.getServiceName(),
                entity.getPassword(),
                entity.getAuthorityEntity().getAuthority(),
                entity.isLocked()
        );
    }
}
