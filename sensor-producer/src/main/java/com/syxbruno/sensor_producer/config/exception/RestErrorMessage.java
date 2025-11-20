package com.syxbruno.sensor_producer.config.exception;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class RestErrorMessage {

  private HttpStatus status;
  private String message;
  private String path;
  private String origin;
  private Instant timestamp;
}
