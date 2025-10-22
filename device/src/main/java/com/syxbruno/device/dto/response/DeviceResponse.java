package com.syxbruno.device.dto.response;

import com.syxbruno.device.model.Location;
import com.syxbruno.device.model.TypeSensor;
import java.time.Instant;
import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Builder
public record DeviceResponse(
    String name,
    Location location,
    TypeSensor type,
    Boolean active,
    Instant registeredAt
) {

  @Override
  public boolean equals(Object o) {

    return EqualsBuilder.reflectionEquals(o, this);
  }
}
