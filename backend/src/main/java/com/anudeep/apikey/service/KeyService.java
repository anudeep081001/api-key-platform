package com.anudeep.apikey.service;

import com.anudeep.apikey.model.ApiKey;
import com.anudeep.apikey.model.Customer;
import com.anudeep.apikey.repo.ApiKeyRepo;
import com.anudeep.apikey.repo.CustomerRepo;
import com.anudeep.apikey.security.Hashing;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class KeyService {
  private final CustomerRepo customers;
  private final ApiKeyRepo keys;
  private final SecureRandom rand = new SecureRandom();

  public KeyService(CustomerRepo customers, ApiKeyRepo keys){
    this.customers = customers;
    this.keys = keys;
  }

  public Customer getOrCreateCustomer(String email){
    return customers.findByEmail(email).orElseGet(() -> customers.save(new Customer(email)));
  }

  public IssuedKey issueKey(String email){
    Customer c = getOrCreateCustomer(email);
    String raw = genRawKey();
    String hash = Hashing.sha256(raw);
    keys.save(new ApiKey(c, hash));
    return new IssuedKey(c.getId(), raw);
  }

  public IssuedKey rotate(String rawOld){
    String oldHash = Hashing.sha256(rawOld);
    ApiKey existing = keys.findByKeyHashAndActiveTrue(oldHash).orElseThrow(() -> new IllegalArgumentException("Invalid key"));
    existing.setActive(false);
    keys.save(existing);
    String rawNew = genRawKey();
    keys.save(new ApiKey(existing.getCustomer(), Hashing.sha256(rawNew)));
    return new IssuedKey(existing.getCustomer().getId(), rawNew);
  }

  private String genRawKey(){
    byte[] b = new byte[24];
    rand.nextBytes(b);
    return "ak_" + Base64.getUrlEncoder().withoutPadding().encodeToString(b);
  }

  public record IssuedKey(Long customerId, String apiKey){}
}
