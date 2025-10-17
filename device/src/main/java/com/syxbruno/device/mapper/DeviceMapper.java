package com.syxbruno.device.mapper;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.model.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

  DeviceResponse toDeviceResponse(Device device);
  Device toDevice(DeviceRegisterRequest device);
}
