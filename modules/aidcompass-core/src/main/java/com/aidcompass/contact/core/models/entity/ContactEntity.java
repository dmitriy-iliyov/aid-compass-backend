package com.aidcompass.contact.core.models.entity;

import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@Data
@Accessors(fluent = false)
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    @Id
    @SequenceGenerator(name = "cont_seq", sequenceName = "cont_seq", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_seq")
    private Long id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ContactTypeEntity typeEntity;

    @Column(name = "contact", unique = true, nullable = false)
    private String contact;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @Column(name = "is_linked_to", nullable = false)
    private boolean isLinkedToAccount;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public ContactEntity(UUID ownerId,
                         ContactTypeEntity typeEntity,
                         String contact,
                         boolean isPrimary,
                         boolean isConfirmed,
                         boolean isLinkedToAccount, Instant createdAt, Instant updatedAt) {
        this.ownerId = ownerId;
        this.typeEntity = typeEntity;
        this.contact = contact;
        this.isPrimary = isPrimary;
        this.isConfirmed = isConfirmed;
        this.isLinkedToAccount = isLinkedToAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @PrePersist
    public void prePersist() {
        Instant time = Instant.now();
        this.createdAt = time;
        this.updatedAt = time;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
