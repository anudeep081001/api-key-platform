package com.anudeep.apikey.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="customers")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true)
  private String email;

  @Column(nullable=false)
  private Instant createdAt = Instant.now();

  public Customer() {}
  public Customer(String email){ this.email = email; }

  public Long getId(){ return id; }
  public String getEmail(){ return email; }
  public Instant getCreatedAt(){ return createdAt; }
}
