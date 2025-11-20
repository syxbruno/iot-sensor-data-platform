package com.syxbruno.sensor_producer.dto;

import java.time.Instant;

public record TemperatureOnlyData(
    String deviceName,
    int temperature,
    String unit,
    Instant timesTamp) {

}
