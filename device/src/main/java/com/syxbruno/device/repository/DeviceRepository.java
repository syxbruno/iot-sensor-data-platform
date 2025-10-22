package com.syxbruno.device.repository;

import com.syxbruno.device.model.Device;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

  Page<Device> findAllByOrderByRegisteredAtDesc(Pageable pageable);

  Optional<Device> findByName(String name);

  boolean existsByName(String name);
}
