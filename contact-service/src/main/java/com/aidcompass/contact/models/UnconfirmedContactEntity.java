package com.aidcompass.contact.models;

import com.aidcompass.contact_type.models.ContactType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash
public class UnconfirmedContactEntity {

    @Id
    private String contact;
    private ContactType type;

    @JsonProperty("owner_id")
    private UUID ownerId;


    @JsonCreator
    public UnconfirmedContactEntity(@JsonProperty("contact") String contact,
                                    @JsonProperty("type") ContactType type,
                                    @JsonProperty("owner_id") UUID ownerId) {
        this.contact = contact;
        this.type = type;
        this.ownerId = ownerId;
    }
}
