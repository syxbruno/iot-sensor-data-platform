package com.syxbruno.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DeviceApplication {

  public static void main(String[] args) {

    SpringApplication.run(DeviceApplication.class, args);
  }
}
