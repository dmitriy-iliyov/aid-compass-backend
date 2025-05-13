package com.aidcompass.customer;

import com.aidcompass.customer.models.CustomerEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @EntityGraph(attributePaths = {"profileStatus"})
    Optional<CustomerEntity> findWithStatusEntityByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
