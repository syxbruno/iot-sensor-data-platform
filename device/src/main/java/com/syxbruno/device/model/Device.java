package com.syxbruno.device.model;

import com.syxbruno.device.enums.TypeSensor;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "device")
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  @Embedded
  private Location location;
  @Enumerated(EnumType.STRING)
  private TypeSensor type;
  private Boolean active;
  private Instant registeredAt;
  private Instant updatedAt;
}