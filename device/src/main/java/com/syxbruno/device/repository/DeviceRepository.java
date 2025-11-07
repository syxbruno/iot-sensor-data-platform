package com.syxbruno.device.repository;

import com.syxbruno.device.model.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

  List<Device> findAllByOrderByRegisteredAtDesc();

  Optional<Device> findByName(String name);

  boolean existsByName(String name);

  void deleteByName(String name);
}
