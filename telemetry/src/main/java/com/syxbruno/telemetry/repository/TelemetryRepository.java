package com.syxbruno.telemetry.repository;

import com.syxbruno.telemetry.model.Telemetry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

    Page<Telemetry> findBySensorNameAndTimestampBetween(String name, Instant start, Instant end, Pageable pageable);
}