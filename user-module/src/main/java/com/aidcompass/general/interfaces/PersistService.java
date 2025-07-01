package com.aidcompass.general.interfaces;

import com.aidcompass.detail.models.DetailEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PersistService<T, D> {
    D save(UUID id, DetailEntity detailEntity, T dto);
}
