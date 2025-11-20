package com.syxbruno.sensor_producer.controller;

import com.syxbruno.sensor_producer.service.SensorProducerService;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/send-data")
public class SensorProducerController {

  private final SensorProducerService sensorProducerService;

  @PostMapping
  public ResponseEntity<String> receivesSensorData(
      @RequestBody @NotNull(message = "the sensor data cannot be null.") Map<String, String> data
  ) {
    sensorProducerService.validateSensorData(data);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("sensor data was read successfully");
  }
}