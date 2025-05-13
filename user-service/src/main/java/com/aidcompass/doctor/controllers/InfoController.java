package com.aidcompass.doctor.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors/info")
public class InfoController {

    @GetMapping("/enums")
    public ResponseEntity<?> getEnums() {
        Map<String, List<String>> enums = new HashMap<>();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enums);
    }

}
