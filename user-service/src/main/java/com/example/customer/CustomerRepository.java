package com.example.customer;

import com.example.customer.models.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Modifying
    @Query(value = "UPDATE customer SET contacts = cast(:contacts as jsonb) WHERE id = :id", nativeQuery = true)
    void updateContactsById(UUID id, @Param("contacts") String contactsJson);
}
