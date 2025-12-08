package com.aidcompass.core.contact.contact_type;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactTypeEntity, Integer> {
    ContactTypeEntity getReferenceByType(ContactType type);
}
