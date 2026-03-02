package com.anudeep.apikey.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="usage_events")
public class UsageEvent {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  private Customer customer;

  @Column(nullable=false)
  private String path;

  @Column(nullable=false)
  private int status;

  @Column(nullable=false)
  private Instant ts = Instant.now();

  public UsageEvent(){}
  public UsageEvent(Customer c, String path, int status){
    this.customer = c; this.path = path; this.status = status;
  }
}
