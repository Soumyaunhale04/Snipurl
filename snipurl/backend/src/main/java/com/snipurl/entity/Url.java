package com.snipurl.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Url {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long Id;
  private String originalurl;
  private String shortcode;
  private LocalDateTime createdTime;
  private LocalDateTime expiryTime;
}
