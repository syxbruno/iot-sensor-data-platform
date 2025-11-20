package com.syxbruno.device.service;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.exception.ResourceNotFoundException;
import com.syxbruno.device.mapper.DeviceMapper;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

  private final DeviceRepository repository;
  private final DeviceMapper mapper;

  @Cacheable(value = "AllDevices")
  public List<Device> findAllDevices() {

    return repository.findAllByOrderByRegisteredAtDesc();
  }

  @Cacheable(value = "DeviceByName", key = "#name")
  public Device findDeviceByName(String name) {

    return repository.findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(
            "device with the name: %s, not found".formatted(name)
        ));
  }

  @Transactional
  @CacheEvict(value = {"AllDevices", "DeviceByName"}, allEntries = true)
  public DeviceResponse saveDevice(DeviceRegisterRequest deviceRequest) {

    Optional<Device> deviceFound = repository.findByName(deviceRequest.getName());

    if (deviceFound.isPresent()) {

      return mapper.toDeviceResponse(deviceFound.get());
    }

    Device device = mapper.toDevice(deviceRequest);
    device.setActive(true);
    device.setRegisteredAt(Instant.now());

    Device deviceSaved = repository.save(device);
    return mapper.toDeviceResponse(deviceSaved);
  }

  @Transactional
  @CacheEvict(value = {"AllDevices", "DeviceByName"}, allEntries = true)
  public DeviceResponse updateDeviceByName(String name, DeviceRegisterRequest deviceRequest) {

    Device deviceSaved = findDeviceByName(name);

    deviceSaved.setUpdatedAt(Instant.now());
    deviceSaved.setName(deviceRequest.getName());
    deviceSaved.setLocation(deviceRequest.getLocation());
    deviceSaved.setType(deviceRequest.getType());

    repository.save(deviceSaved);
    return mapper.toDeviceResponse(deviceSaved);
  }

  @Transactional
  @CacheEvict(value = {"AllDevices", "DeviceByName"}, allEntries = true)
  public void deleteDeviceByName(String name) {

    repository.deleteByName(findDeviceByName(name).getName());
  }

  @Transactional
  @CacheEvict(value = {"AllDevices", "DeviceByName"}, allEntries = true)
  public void activeSensor(String name) {

    Device deviceSaved = findDeviceByName(name);
    deviceSaved.setActive(true);
    repository.save(deviceSaved);
  }

  @Transactional
  @CacheEvict(value = {"AllDevices", "DeviceByName"}, allEntries = true)
  public void disableSensor(String name) {

    Device deviceSaved = findDeviceByName(name);
    deviceSaved.setActive(false);
    repository.save(deviceSaved);
  }
}