package com.syxbruno.device.service;

import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_1;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_REGISTER_REQUEST;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_REGISTER_REQUEST_TO_UPDATE;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_RESPONSE;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_RESPONSE_UPDATED;

import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.dto.response.DeviceResponse;
import com.syxbruno.device.enums.TypeSensor;
import com.syxbruno.device.exception.ResourceNotFoundException;
import com.syxbruno.device.mapper.DeviceMapper;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.model.Location;
import com.syxbruno.device.repository.DeviceRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeviceServiceTest {

  @InjectMocks
  private DeviceService deviceService;
  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private DeviceMapper deviceMapper;

  @Test
  @Order(1)
  public void findAllDevicesReturnsListDevice() {

    List<Device> deviceList = List.of(DEVICE_1, DEVICE_1);

    Mockito.when(deviceRepository.findAllByOrderByRegisteredAtDesc()).thenReturn(deviceList);

    List<Device> sut = deviceService.findAllDevices();

    Assertions.assertThat(sut).isNotNull();
    Assertions.assertThat(sut).isNotEmpty();
    Assertions.assertThat(sut).hasSize(2);
  }

  @Test
  @Order(2)
  public void findAllDevices_ReturnsEmptyListDevice() {

    Mockito.when(deviceRepository.findAllByOrderByRegisteredAtDesc()).thenReturn(List.of());

    List<Device> sut = deviceService.findAllDevices();

    Assertions.assertThat(sut).isNotNull();
    Assertions.assertThat(sut).isEmpty();
    Assertions.assertThat(sut).hasSize(0);
  }

  @Test
  @Order(3)
  public void findDeviceByName_ReceiveName_ReturnDeviceByNameIfExistsInDatabase() {

    String deviceName = "device1";
    Device device = DEVICE_1;

    Mockito.when(deviceRepository.findByName(deviceName))
        .thenReturn(Optional.of(device));

    Device sut = deviceService.findDeviceByName(deviceName);

    Assertions.assertThat(sut).isEqualTo(device);
  }

  @Test
  @Order(4)
  public void findDeviceByName_ReceiveName_ReturnDeviceByNameIfNotExistsInDatabase() {

    String deviceName = "device1";

    Mockito.when(deviceRepository.findByName(deviceName))
        .thenReturn(Optional.empty());

    Assertions.assertThatThrownBy(() -> deviceService.findDeviceByName(deviceName))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("device with the name: %s, not found".formatted(deviceName));
  }

  @Test
  @Order(5)
  public void saveDevice_ReceiveDeviceRegisterRequest_ReturnsDeviceIfAlreadyExistsInDatabase() {

    Device device = DEVICE_1;
    DeviceRegisterRequest deviceToSave = DEVICE_REGISTER_REQUEST;
    DeviceResponse expectedResponse = DEVICE_RESPONSE;

    Mockito.when(deviceMapper.toDeviceResponse(device)).thenReturn(expectedResponse);
    Mockito.when(deviceRepository.findByName(device.getName())).thenReturn(Optional.of(device));

    DeviceResponse sut = deviceService.saveDevice(deviceToSave);

    Assertions.assertThat(sut)
        .isEqualTo(expectedResponse);
  }

  @Test
  @Order(6)
  public void saveDevice_ReceiveDeviceRegisterRequest_ReturnsDeviceIfNotExistsInDatabase() {

    Device device = DEVICE_1;
    DeviceRegisterRequest deviceToSave = DEVICE_REGISTER_REQUEST;
    DeviceResponse expectedResponse = DEVICE_RESPONSE;

    Mockito.when(deviceRepository.findByName(deviceToSave.getName())).thenReturn(Optional.empty());
    Mockito.when(deviceRepository.findByName(deviceToSave.getName()))
        .thenReturn(Optional.of(device));
    Mockito.when(deviceMapper.toDeviceResponse(device)).thenReturn(expectedResponse);

    DeviceResponse sut = deviceService.saveDevice(deviceToSave);

    Assertions.assertThat(sut)
        .isEqualTo(expectedResponse);
  }

  @Test
  @Order(7)
  void updateDeviceByName_ReceiveNameAndDeviceRegisterRequest_ReturnDeviceUpdated() {

    Device device = DEVICE_1;
    String deviceName = device.getName();

    Mockito.when(deviceRepository.findByName(deviceName)).thenReturn(Optional.of(device));
    Mockito.when(deviceMapper.toDeviceResponse(Mockito.any(Device.class)))
        .thenReturn(DEVICE_RESPONSE_UPDATED);

    DeviceResponse sut = deviceService
        .updateDeviceByName(deviceName, DEVICE_REGISTER_REQUEST_TO_UPDATE);

    Assertions.assertThat(sut.getName()).isEqualTo("deviceToUpdate");
    Assertions.assertThat(sut.getLocation()).isEqualTo(new Location(-22.2222222, -22.2222222));
    Assertions.assertThat(sut.getType()).isEqualTo(TypeSensor.TEMPERATURE_HUMIDITY);
  }

  @Test
  @Order(8)
  public void deleteDeviceByName_ReceiveNameSensor_NoReturn() {

    String deviceName = "device1";

    Mockito.when(deviceRepository.findByName(deviceName))
        .thenReturn(Optional.of(DEVICE_1));

    Assertions.assertThatCode(() -> deviceService.deleteDeviceByName(deviceName))
        .doesNotThrowAnyException();
  }

  @Test
  @Order(9)
  public void activeSensor_ReceiveNameSensor_NoReturn() {

    Device device = DEVICE_1;
    device.setActive(false);

    Mockito.when(deviceRepository.findByName(device.getName())).thenReturn(Optional.of(device));

    deviceService.activeSensor(device.getName());

    Assertions.assertThat(device.getActive()).isTrue();
  }

  @Test
  @Order(10)
  public void disableSensor_ReceiveNameSensor_NoReturn() {

    Device device = DEVICE_1;
    device.setActive(true);

    Mockito.when(deviceRepository.findByName(device.getName())).thenReturn(Optional.of(device));

    deviceService.disableSensor(device.getName());

    Assertions.assertThat(device.getActive()).isFalse();
  }
}