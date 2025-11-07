package com.syxbruno.device.dto.request;

import com.syxbruno.device.enums.TypeSensor;
import com.syxbruno.device.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRegisterRequest {

  @NotBlank(message = "the name cannot be null or empty")
  private String name;

  @NotNull(message = "the location cannot be null")
  private Location location;

  @NotNull(message = "the type cannot be null")
  private TypeSensor type;
}