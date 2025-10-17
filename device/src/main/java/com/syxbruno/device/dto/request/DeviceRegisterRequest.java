package com.syxbruno.device.dto.request;

import com.syxbruno.device.model.Location;
import com.syxbruno.device.model.TypeSensor;

public record DeviceRegisterRequest(
    String name,
    Location location,
    TypeSensor type
) {

}
