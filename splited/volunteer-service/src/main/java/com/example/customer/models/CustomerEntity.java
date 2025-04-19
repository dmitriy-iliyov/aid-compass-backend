package com.example.customer.models;


import com.example.contacts.models.ContactsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class CustomerEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "contacts")
    @JdbcTypeCode(SqlTypes.JSON)
    private ContactsEntity contacts;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @PrePersist
    public void beforeCreate(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt = Instant.now();
    }
}
