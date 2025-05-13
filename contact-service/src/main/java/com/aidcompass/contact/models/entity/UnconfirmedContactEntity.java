package com.aidcompass.contact.models.entity;

import com.aidcompass.contact_type.models.ContactType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

// связка происходит с внутрененим auth id пользоввателя а поиск производитьс я по публичному клюючу
@RedisHash(value = "cont:uncf:", timeToLive = 86400)
public class UnconfirmedContactEntity {

    @Id
    private final String contact;
    private final ContactType type;

    @JsonProperty("owner_id")
    private final UUID userId;


    @JsonCreator
    public UnconfirmedContactEntity(@JsonProperty("contact") String contact,
                                    @JsonProperty("type") ContactType type,
                                    @JsonProperty("owner_id") UUID userId) {
        this.contact = contact;
        this.type = type;
        this.userId = userId;
    }
}
