package com.syxbruno.device.controller;

import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_1;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_REGISTER_REQUEST;
import static com.syxbruno.device.commons.constant.ConstantDevice.DEVICE_RESPONSE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syxbruno.device.dto.request.DeviceRegisterRequest;
import com.syxbruno.device.model.Device;
import com.syxbruno.device.service.DeviceService;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DeviceController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeviceControllerTest {

  private final String URI = "/device";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private DeviceService deviceService;

  @Test
  @Order(1)
  public void findAllDevices_ReturnsListDevice() throws Exception {

    List<Device> deviceList = List.of(DEVICE_1);

    Mockito.when(deviceService.findAllDevices()).thenReturn(deviceList);

    mockMvc.perform(MockMvcRequestBuilders.get(URI)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(deviceList.size()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("device1"));
  }

  @Test
  @Order(2)
  public void findDeviceByName_ReceiveName_ReturnDeviceByName() throws Exception {

    String expectedResponse = objectMapper.writeValueAsString(DEVICE_1);

    Mockito.when(deviceService.findDeviceByName("device1")).thenReturn(DEVICE_1);

    mockMvc
        .perform(
            MockMvcRequestBuilders
                .get(URI + "/device1")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
  }

  @Test
  @Order(3)
  public void saveDevice_ReceiveDeviceRegisterRequest_ReturnsDeviceSaved() throws Exception {

    String expectedResponse = objectMapper.writeValueAsString(DEVICE_RESPONSE);

    Mockito.when(deviceService.saveDevice(Mockito.any(DeviceRegisterRequest.class)))
        .thenReturn(DEVICE_RESPONSE);

    mockMvc
        .perform(
            MockMvcRequestBuilders
                .post(URI)
                .content(objectMapper.writeValueAsString(DEVICE_REGISTER_REQUEST))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
  }

  @Test
  @Order(4)
  public void updateDeviceByName_ReceiveDeviceNameAndDeviceRegisterRequest_ReturnDeviceResponseUpdated()
      throws Exception {

    Mockito.when(
            deviceService.updateDeviceByName(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(DeviceRegisterRequest.class)
            )
        )
        .thenReturn(DEVICE_RESPONSE);

    mockMvc
        .perform(
            MockMvcRequestBuilders
                .patch(URI + "/device1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DEVICE_REGISTER_REQUEST))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("device1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("HUMIDITY_ONLY"));
  }

  @Test
  @Order(5)
  public void deleteDeviceByName_ReceiveNameDevice_NoReturn() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/device1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @Order(6)
  public void activeSensor_ReceiveDeviceName_ReturnStringMessage() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.patch(URI + "/active/device1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers
                .content()
                .string("the sensor has been activated successfully")
        );
  }

  @Test
  @Order(7)
  public void disableSensor_ReceiveDeviceName_ReturnStringMessage() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.patch(URI + "/disable/device1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers
                .content()
                .string("the sensor was successfully disabled")
        );
  }
}