package com.syxbruno.device.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class Location {

  private Double latitude;
  private Double longitude;
}
