package com.syxbruno.telemetry.controller;

import com.syxbruno.telemetry.dto.request.SensorDataRequest;
import com.syxbruno.telemetry.model.Telemetry;
import com.syxbruno.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Validated
@RestController
@RequestMapping("/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService service;

    @PostMapping("/reading")
    public ResponseEntity<Void> registerReading(@RequestBody @Valid SensorDataRequest data) {

        service.registerReading(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Page<Telemetry>> findAllDataBySensorName(
            @PathVariable @NotBlank(message = "the field cannot be empty or null") String name,
            @RequestParam(required = false) @PastOrPresent(message = "timestamp cannot be in the future") LocalDateTime startTime,
            @RequestParam(required = false) @PastOrPresent(message = "timestamp cannot be in the future") LocalDateTime endTime,
            @RequestParam ZoneId zone,
            @PageableDefault(page = 0, size = 10) Pageable pageable
            ) {

        Page<Telemetry> response = service.findAllDataBySensorName(name, startTime, endTime, zone, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
