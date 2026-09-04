package com.syxbruno.telemetry.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorDataRequest {

    @NotBlank(message = "the name sensor cannot be null or empty")
    private String sensorName;

    @NotBlank(message = "the field cannot be null or empty")
    private String field;

    @NotNull(message = "the location cannot be null")
    private double value;
}