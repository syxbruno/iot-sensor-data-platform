package com.syxbruno.device;

import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_REGISTER_REQUEST;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_REGISTER_REQUEST_TO_UPDATE;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_RESPONSE;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_RESPONSE_UPDATED;

import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.model.Device;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/init/insert-data-device.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
    "/init/truncate-table-device.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class DeviceIT {

  @Container
  private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.36")
      .withDatabaseName("iot_sensor_db_test")
      .withUsername("test")
      .withPassword("test");
  private final String uri = "/device";
  @Autowired
  private TestRestTemplate rest;

  @DynamicPropertySource
  private static void configure(DynamicPropertyRegistry registry) {

    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }

  @Test
  void findAllDevices_ReturnOkAndListDevice() {

    ResponseEntity<Device[]> sut = rest.getForEntity(uri, Device[].class);

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<Device> devices = Arrays.asList(Objects.requireNonNull(sut.getBody()));

    Assertions.assertThat(devices).isNotEmpty();

    List<String> names = devices.stream()
        .map(Device::getName)
        .toList();

    Assertions.assertThat(names).containsExactlyInAnyOrder("device1", "device2");
  }

  @Test
  public void findDeviceByName_ReturnOkAndDeviceResponseByName() {

    ResponseEntity<DeviceResponse> sut = rest.getForEntity(uri + "/device1", DeviceResponse.class);

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(sut.getBody()).isEqualTo(DEVICE_RESPONSE);
  }

  @Test
  public void saveDevice_ReturnCreatedAndDeviceResponse() {

    ResponseEntity<DeviceResponse> sut = rest
        .postForEntity(uri, DEVICE_REGISTER_REQUEST, DeviceResponse.class);

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    Assertions.assertThat(sut.getBody())
        .usingRecursiveComparison()
        .ignoringFields("registeredAt")
        .isEqualTo(DEVICE_RESPONSE);
  }

  @Test
  public void updateDevice_ReturnOkAndDeviceResponse() {

    ResponseEntity<DeviceResponse> sut = rest.exchange(
        uri + "/device1",
        HttpMethod.PATCH,
        new HttpEntity<>(DEVICE_REGISTER_REQUEST_TO_UPDATE),
        DeviceResponse.class
    );

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

    Assertions.assertThat(sut.getBody())
        .usingRecursiveComparison()
        .ignoringFields("registeredAt", "updatedAt")
        .isEqualTo(DEVICE_RESPONSE_UPDATED);
  }

  @Test
  public void deleteDevice_ReturnNoContent() {

    ResponseEntity<Void> sut = rest.exchange(
        uri + "/device1",
        HttpMethod.DELETE,
        null,
        Void.class
    );

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    Assertions.assertThat(sut.getBody()).isNull();
  }

  @Test
  public void activeSensor_ReturnOkAndString() {

    ResponseEntity<String> sut = rest.exchange(
        uri + "/active/device1",
        HttpMethod.PATCH,
        null,
        String.class
    );

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(sut.getBody()).contains("the sensor has been activated successfully");
  }

  @Test
  public void disableSensor_ReturnOkAndString() {

    ResponseEntity<String> sut = rest.exchange(
        uri + "/disable/device1",
        HttpMethod.PATCH,
        null,
        String.class
    );

    Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(sut.getBody()).contains("the sensor was successfully disabled");
  }
}