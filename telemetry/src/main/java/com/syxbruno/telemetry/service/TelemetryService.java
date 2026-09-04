package com.syxbruno.telemetry.service;

import com.syxbruno.telemetry.dto.request.SensorDataRequest;
import com.syxbruno.telemetry.mapper.TelemetryMapper;
import com.syxbruno.telemetry.model.Telemetry;
import com.syxbruno.telemetry.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository repository;
    private final TelemetryMapper mapper;

    public void registerReading(SensorDataRequest data) {

        Telemetry telemetry = mapper.toTelemetry(data);
        telemetry.setTimestamp(Instant.now());

        repository.save(telemetry);
    }

    public Page<Telemetry> findAllDataBySensorName(String name, LocalDateTime startTime, LocalDateTime endTime, ZoneId zone, Pageable pageable) {

        Instant now = Instant.now();

        Instant start = Optional.ofNullable(localDateTimeAtZoneToInstant(startTime, zone)).orElse(now.minus(Duration.ofDays(3)));
        Instant end = Optional.ofNullable(localDateTimeAtZoneToInstant(endTime, zone)).orElse(now);

        return repository.findBySensorNameAndTimestampBetween(name, start, end, pageable);
    }

    private Instant localDateTimeAtZoneToInstant(LocalDateTime date, ZoneId zone) {

        if (date != null) {

            return date.atZone(zone).toInstant();
        }

        return null;
    }
}