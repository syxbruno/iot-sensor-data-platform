package com.syxbruno.sensor_producer.dto;

import java.time.Instant;

public record TemperatureHumidityData(
    String deviceName,
    int temperature,
    int humidity,
    String unit,
    Instant timesTamp
) {

}
