package com.syxbruno.sensor_producer.config.exception;

import com.syxbruno.sensor_producer.exception.InvalidDataException;
import com.syxbruno.sensor_producer.exception.IsNotSupportedSensorException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidDataException.class)
  private ResponseEntity<RestErrorMessage> handleInvalidData(InvalidDataException ex,
      HttpServletRequest request) {

    RestErrorMessage response = RestErrorMessage.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .origin(getOrigin(ex))
        .timestamp(Instant.now())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(IsNotSupportedSensorException.class)
  private ResponseEntity<RestErrorMessage> handleIsNotSupportedSensor(
      IsNotSupportedSensorException ex,
      HttpServletRequest request) {

    RestErrorMessage response = RestErrorMessage.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .origin(getOrigin(ex))
        .timestamp(Instant.now())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  private String getOrigin(Throwable ex) {

    for (StackTraceElement element : ex.getStackTrace()) {

      if (element.getClassName().startsWith("com.syxbruno.device")) {

        return "%s.%s.%d".formatted(element.getClassName(), element.getMethodName(),
            element.getLineNumber());
      }
    }
    return "";
  }
}
