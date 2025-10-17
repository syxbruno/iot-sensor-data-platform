package com.syxbruno.device.service;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.exception.ResourceNotFoundException;
import com.syxbruno.device.mapper.DeviceMapper;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.repository.DeviceRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

  private final DeviceRepository repository;
  private final DeviceMapper mapper;

  public Page<Device> findAllDevices(int page, int size) {

    PageRequest pageable = PageRequest.of(page, size);
    return repository.findAllByOrderByRegisteredAtDesc(pageable);
  }

  public Device findDeviceByName(String name) {

    return repository.findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("device with the name: %s, not found".formatted(name)));
  }

  public DeviceResponse saveDevice(DeviceRegisterRequest deviceRequest) {

    Optional<Device> deviceFound = repository.findByName(deviceRequest.name());

    if (deviceFound.isPresent()) {

      return mapper.toDeviceResponse(deviceFound.get());
    }

    Device device = mapper.toDevice(deviceRequest);
    activeSensor(device.getName());
    device.setRegisteredAt(Instant.from(LocalDateTime.now()));
    Device deviceSaved = repository.save(device);

    return mapper.toDeviceResponse(deviceSaved);
  }

  public DeviceResponse updateDeviceByName(String name, DeviceRegisterRequest deviceRequest) {

    Device deviceSaved = findDeviceByName(name);

    deviceSaved.setUpdatedAt(Instant.now());
    deviceSaved.setName(deviceRequest.name());
    deviceSaved.setLocation(deviceRequest.location());
    deviceSaved.setType(deviceRequest.type());

    repository.save(deviceSaved);

    return mapper.toDeviceResponse(deviceSaved);
  }

  public void deleteDeviceByName(String name) {

    repository.delete(findDeviceByName(name));
  }

  public void activeSensor(String name) {

    Device deviceSaved = findDeviceByName(name);
    deviceSaved.setActive(true);

    repository.save(deviceSaved);
  }

  public void disableSensor(String name) {

    Device deviceSaved = findDeviceByName(name);
    deviceSaved.setActive(false);

    repository.save(deviceSaved);
  }
}