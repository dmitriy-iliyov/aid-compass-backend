package com.aidcompass.contact.models.entity;

import com.aidcompass.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash(value = "cont:uncf:", timeToLive = 86400)
@Data
public class UnconfirmedContactEntity {

    @Id
    private final String contact;
    private final ContactType type;

    @JsonProperty("owner_id")
    private final UUID ownerId;


    @JsonCreator
    public UnconfirmedContactEntity(@JsonProperty("contact") String contact,
                                    @JsonProperty("type") ContactType type,
                                    @JsonProperty("owner_id") UUID ownerId) {
        this.contact = contact;
        this.type = type;
        this.ownerId = ownerId;
    }
}
