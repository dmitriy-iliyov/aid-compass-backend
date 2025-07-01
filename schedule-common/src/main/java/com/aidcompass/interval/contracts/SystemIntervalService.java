package com.aidcompass.interval.contracts;

import java.util.List;

public interface SystemIntervalService {
    List<Long> deleteBatchBeforeWeakStart(int batchSize);
}
