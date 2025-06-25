package com.aidcompass.interval.services;

import java.util.List;

public interface SystemIntervalService {
    List<Long> deleteBatchBeforeWeakStart(int batchSize);
}
