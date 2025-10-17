package com.syxbruno.device.dto.response;

import java.time.Instant;

public record DeviceResponse(
    String name,
    String location,
    String type,
    Boolean active,
    Instant registeredAt
) {

}
