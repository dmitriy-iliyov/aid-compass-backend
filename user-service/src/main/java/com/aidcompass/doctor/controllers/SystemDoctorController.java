package com.aidcompass.doctor.controllers;

import com.aidcompass.doctor.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/v1/doctor")
@RequiredArgsConstructor
public class SystemDoctorController {

    private final DoctorService doctorService;

    //ендпоинт для оповещения о том что график заполнен

}
