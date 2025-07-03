package com.aidcompass.detail;

import com.aidcompass.detail.models.DetailEntity;

import java.util.UUID;

public interface PersistEmptyDetailService {
    DetailEntity saveEmpty(UUID userId);
}
