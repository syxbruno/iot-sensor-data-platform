package com.syxbruno.device.config.exception;

import com.syxbruno.device.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  private ResponseEntity<RestErrorMessage> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

    RestErrorMessage response = RestErrorMessage.builder()
        .status(HttpStatus.NOT_FOUND)
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .origin(getOrigin(ex))
        .timestamp(Instant.now())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  private String getOrigin(Throwable ex) {

    for (StackTraceElement element : ex.getStackTrace()) {

      if (element.getClassName().startsWith("com.syxbruno.device")) {

        return "%s.%s.%d".formatted(element.getClassName(), element.getMethodName(), element.getLineNumber());
      }
    }

    return "";
  }
}
