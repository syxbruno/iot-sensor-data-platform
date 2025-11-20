package com.syxbruno.sensor_producer.service;

import com.syxbruno.sensor_producer.dto.HumidityOnlyData;
import com.syxbruno.sensor_producer.dto.TemperatureHumidityData;
import com.syxbruno.sensor_producer.dto.TemperatureOnlyData;
import com.syxbruno.sensor_producer.enums.TypeSensor;
import com.syxbruno.sensor_producer.exception.InvalidDataException;
import com.syxbruno.sensor_producer.exception.IsNotSupportedSensorException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SensorProducerService {

  public void validateSensorData(Map<String, String> data) {

    String deviceName = data.get("deviceName");
    String sensorTypeStr = data.get("type");
    String timesTampStr = data.get("timestamp");

    String[] values = {deviceName, sensorTypeStr, timesTampStr};

    if (isNullOrBlank(values)) {

      throw new InvalidDataException("required fields missing or incorrect.");
    }

    TypeSensor sensorType;
    Instant timesTamp;

    try {

      timesTamp = Instant.parse(timesTampStr);
      sensorType = TypeSensor.valueOf(sensorTypeStr);

    } catch (DateTimeParseException e) {

      throw new InvalidDataException("invalid timestamp or sensor type");
    }

    // utilizar o kafka para verificar se o deviceName existe no banco

    Object dto;

    try {
      switch (sensorType) {

        case HUMIDITY_ONLY -> {

          int humidity = Integer.parseInt(data.get("humidity"));
          String unit = data.get("unit");

          if (!unit.equals("C")) {

            throw new InvalidDataException("invalid unit of measurement");
          }

          dto = new HumidityOnlyData(deviceName, humidity, unit, timesTamp);
        }

        case TEMPERATURE_ONLY -> {

          int temperature = Integer.parseInt(data.get("temperature"));
          String unit = data.get("unit");

          if (!unit.equals("%")) {

            throw new InvalidDataException("invalid unit of measurement");
          }

          dto = new TemperatureOnlyData(deviceName, temperature, unit, timesTamp);
        }

        case TEMPERATURE_HUMIDITY -> {

          int humidity = Integer.parseInt(data.get("humidity"));
          int temperature = Integer.parseInt(data.get("temperature"));
          String unit = data.get("unit");

          if (List.of("C", "%").contains(unit)) {

            throw new InvalidDataException("invalid unit of measurement");
          }

          dto = new TemperatureHumidityData(deviceName, temperature, humidity, unit, timesTamp);
        }

        default -> throw new IsNotSupportedSensorException("the sensor type is not supported");
      }
    } catch (InvalidDataException e) {

      throw new InvalidDataException("data recorded by the sensor is invalid or missing");
    }

    // enviar pro kafka o device com base no seu tipo (criar um metodo especifico pra isso e chamar aqui)
  }

  private boolean isNullOrBlank(String[] values) {

    for (String value : values) {

      if (value == null || value.isBlank()) {

        return true;
      }
    }
    return false;
  }
}