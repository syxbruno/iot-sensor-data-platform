package com.syxbruno.device.dto.response;

import com.syxbruno.device.enums.TypeSensor;
import com.syxbruno.device.model.Location;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceResponse {

  private String name;
  private Location location;
  private TypeSensor type;
  private Boolean active;
  private Instant registeredAt;
}