package com.syxbruno.telemetry.service;

import com.syxbruno.telemetry.dto.request.SensorData;
import com.syxbruno.telemetry.mapper.TelemetryMapper;
import com.syxbruno.telemetry.model.Telemetry;
import com.syxbruno.telemetry.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository repository;
    private final TelemetryMapper mapper;

    public void registerReading(SensorData data) {

        Telemetry telemetry = mapper.toTelemetry(data);
        telemetry.setTimestamp(Instant.now());

        repository.save(telemetry);
    }
}
