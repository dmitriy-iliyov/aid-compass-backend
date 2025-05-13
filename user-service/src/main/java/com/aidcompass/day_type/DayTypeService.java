package com.aidcompass.day_type;

import com.aidcompass.day_type.models.DayType;
import com.aidcompass.day_type.models.DayTypeEntity;
import com.aidcompass.exceptions.DayTypeEntityNotFoundByDayTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayTypeService {

    private final DayTypeRepository repository;
    private final DayTypeMapper mapper;


    @Transactional
    public void saveAll(List<DayType> dayTypeList) {
        List<DayTypeEntity> entityList = mapper.toEntityList(dayTypeList);
        repository.saveAll(entityList);
    }

    @Cacheable(value = "day_type", key = "#dayType.toString()")
    @Transactional(readOnly = true)
    public DayTypeEntity findByDayType(DayType dayType) {
        return repository.findByDayType(dayType).orElseThrow(DayTypeEntityNotFoundByDayTypeException::new);
    }
}
