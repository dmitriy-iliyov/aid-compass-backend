package com.aidcompass.day_type;

import com.aidcompass.day_type.models.DayType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DayTypeDatabaseInitializer {

    private final DayTypeService service;


    @PostConstruct
    public void setUpDayTypes() {
        List<DayType> dayTypeList = new ArrayList<>(List.of(DayType.values()));
        service.saveAll(dayTypeList);
    }
}
