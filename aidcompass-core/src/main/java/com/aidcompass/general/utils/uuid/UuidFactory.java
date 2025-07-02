package com.aidcompass.general.utils.uuid;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UuidFactory {

    public UUID generate() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}
