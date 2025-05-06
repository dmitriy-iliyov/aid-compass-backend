package com.aidcompass.contact.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UnconfirmedContactRepository extends JpaRepository<UUID, UnconfirmedContactService> {
}
