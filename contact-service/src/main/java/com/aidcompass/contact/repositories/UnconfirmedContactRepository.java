package com.aidcompass.contact.repositories;

import com.aidcompass.contact.models.UnconfirmedContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedContactRepository extends CrudRepository<UnconfirmedContactEntity, String> {
}
