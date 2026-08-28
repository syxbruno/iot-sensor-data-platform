package com.syxbruno.device.repository;

import com.syxbruno.device.model.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

  List<Device> findAllByOrderByRegisteredAtDesc();

  Optional<Device> findByName(String name);

  void deleteByName(String name);
}
