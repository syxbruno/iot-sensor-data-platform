package com.syxbruno.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TelemetryApplication {

  public static void main(String[] args) {

    SpringApplication.run(TelemetryApplication.class, args);
  }
}
