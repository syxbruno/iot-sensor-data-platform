package com.syxbruno.device.config.exception;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class RestErrorMessage {

  private HttpStatus status;
  private String message;
  private String path;
  private String origin;
  private Instant timestamp;
}
