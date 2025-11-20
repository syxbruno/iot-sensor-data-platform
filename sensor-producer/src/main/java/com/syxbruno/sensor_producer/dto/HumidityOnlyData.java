package com.syxbruno.sensor_producer.dto;

import java.time.Instant;

public record HumidityOnlyData(
    String deviceName,
    int humidity,
    String unit,
    Instant timestamp
) {

}
