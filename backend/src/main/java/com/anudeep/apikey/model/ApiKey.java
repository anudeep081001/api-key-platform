package com.anudeep.apikey.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="api_keys")
public class ApiKey {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  private Customer customer;

  @Column(nullable=false, unique=true, length=80)
  private String keyHash; // store hash, not raw key

  @Column(nullable=false)
  private boolean active = true;

  @Column(nullable=false)
  private Instant createdAt = Instant.now();

  public ApiKey() {}

  public ApiKey(Customer c, String keyHash){
    this.customer = c;
    this.keyHash = keyHash;
  }

  public Long getId(){ return id; }
  public Customer getCustomer(){ return customer; }
  public String getKeyHash(){ return keyHash; }
  public boolean isActive(){ return active; }
  public void setActive(boolean a){ this.active = a; }
  public Instant getCreatedAt(){ return createdAt; }
}
