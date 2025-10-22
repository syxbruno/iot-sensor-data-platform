package com.syxbruno.device.dto.request;

import com.syxbruno.device.model.Location;
import com.syxbruno.device.model.TypeSensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeviceRegisterRequest(
    @NotBlank(message = "the name cannot be null or empty")
    String name,
    @NotNull(message = "the location cannot be null")
    Location location,
    @NotNull(message = "the type cannot be null")
    TypeSensor type
) {

}
