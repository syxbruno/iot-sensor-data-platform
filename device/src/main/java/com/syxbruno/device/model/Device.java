package com.syxbruno.device.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  @Embedded
  private Location location;
  private TypeSensor type;
  private Boolean active;
  private Instant registeredAt;
  private Instant updatedAt;
}