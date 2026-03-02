package com.anudeep.apikey.repo;

import com.anudeep.apikey.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);
}
