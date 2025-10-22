package com.syxbruno.device.controller;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.service.DeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {

  private final DeviceService service;

  @GetMapping
  public PagedModel<Device> findAllDevices(
      @RequestParam(value = "page", defaultValue = "0") @Positive @NotNull int page,
      @RequestParam(value = "size", defaultValue = "10") @Positive @NotNull int size
  ) {

    Page<Device> all = service.findAllDevices(page, size);
    return new PagedModel<>(all);
  }

  @GetMapping("/{name}")
  public ResponseEntity<Device> findDeviceByName(@PathVariable @NotBlank String name) {

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
  public ResponseEntity<DeviceResponse> updateDeviceByName(@PathVariable @NotBlank String name,
      @RequestBody @Valid DeviceRegisterRequest deviceRequest) {

    DeviceResponse response = service.updateDeviceByName(name, deviceRequest);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{name}")
  public ResponseEntity<Void> deleteDeviceByName(@PathVariable @NotBlank String name) {

    service.deleteDeviceByName(name);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/active/{name}")
  public ResponseEntity<String> activeSensor(@PathVariable @NotBlank String name) {

    service.activeSensor(name);
    return ResponseEntity.status(HttpStatus.OK).body("the sensor has been activated successfully");
  }

  @PatchMapping("/disable/{name}")
  public ResponseEntity<String> disableSensor(@PathVariable @NotBlank String name) {

    service.disableSensor(name);
    return ResponseEntity.status(HttpStatus.OK).body("the sensor was successfully disabled");
  }
}