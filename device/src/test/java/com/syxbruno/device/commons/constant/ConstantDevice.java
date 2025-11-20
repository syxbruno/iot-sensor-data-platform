package com.syxbruno.device.commons.constant;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.enums.TypeSensor;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.model.Location;
import org.springframework.stereotype.Component;

@Component
public class ConstantDevice {

  public static final Device DEVICE_1 = Device.builder()
      .id(1L)
      .name("device1")
      .location(new Location(-11.1111111, -11.1111111))
      .type(TypeSensor.HUMIDITY_ONLY)
      .active(true)
      .registeredAt(null)
      .updatedAt(null)
      .build();


  public static final DeviceRegisterRequest DEVICE_REGISTER_REQUEST = DeviceRegisterRequest.builder()
      .name("device1")
      .location(new Location(-11.1111111, -11.1111111))
      .type(TypeSensor.HUMIDITY_ONLY)
      .build();

  public static final DeviceResponse DEVICE_RESPONSE = DeviceResponse.builder()
      .name("device1")
      .location(new Location(-11.1111111, -11.1111111))
      .type(TypeSensor.HUMIDITY_ONLY)
      .active(true)
      .registeredAt(null)
      .build();

  public static final DeviceRegisterRequest DEVICE_REGISTER_REQUEST_TO_UPDATE = DeviceRegisterRequest.builder()
      .name("deviceToUpdate")
      .location(new Location(-22.2222222, -22.2222222))
      .type(TypeSensor.TEMPERATURE_HUMIDITY)
      .build();

  public static final DeviceResponse DEVICE_RESPONSE_UPDATED = DeviceResponse.builder()
      .name("deviceToUpdate")
      .location(new Location(-22.2222222, -22.2222222))
      .type(TypeSensor.TEMPERATURE_HUMIDITY)
      .active(true)
      .registeredAt(null)
      .build();
}