package com.syxbruno.device.controller;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.service.DeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class DeviceController {

  private final DeviceService service;

  @GetMapping
  public ResponseEntity<List<Device>> findAllDevices() {

    List<Device> response = service.findAllDevices();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{name}")
  public ResponseEntity<Device> findDeviceByName(
      @PathVariable @NotBlank(message = "the field cannot be empty or null") String name
  ) {

    Device response = service.findDeviceByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<DeviceResponse> saveDevice(
      @RequestBody @Valid DeviceRegisterRequest deviceRequest) {

    DeviceResponse response = service.saveDevice(deviceRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/{name}")
  public ResponseEntity<DeviceResponse> updateDeviceByName(
      @PathVariable @NotBlank(message = "the field cannot be empty or null") String name,
      @RequestBody @Valid DeviceRegisterRequest deviceRequest
  ) {

    DeviceResponse response = service.updateDeviceByName(name, deviceRequest);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{name}")
  public ResponseEntity<Void> deleteDeviceByName(
      @PathVariable @NotBlank(message = "the field cannot be empty or null") String name
  ) {

    service.deleteDeviceByName(name);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/active/{name}")
  public ResponseEntity<String> activeSensor(
      @PathVariable @NotBlank(message = "the field cannot be empty or null") String name
  ) {

    service.activeSensor(name);
    return ResponseEntity.status(HttpStatus.OK).body("the sensor has been activated successfully");
  }

  @PatchMapping("/disable/{name}")
  public ResponseEntity<String> disableSensor(
      @PathVariable @NotBlank(message = "the field cannot be empty or null") String name
  ) {

    service.disableSensor(name);
    return ResponseEntity.status(HttpStatus.OK).body("the sensor was successfully disabled");
  }
}