package com.syxbruno.telemetry.controller;

import com.syxbruno.telemetry.dto.request.SensorData;
import com.syxbruno.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService service;

    @PostMapping("/reading")
    public ResponseEntity<Void> registerReading(@RequestBody @Valid SensorData data) {

        service.registerReading(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
