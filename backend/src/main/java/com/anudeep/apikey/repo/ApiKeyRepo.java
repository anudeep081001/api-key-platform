package com.anudeep.apikey.repo;

import com.anudeep.apikey.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepo extends JpaRepository<ApiKey, Long> {
  Optional<ApiKey> findByKeyHashAndActiveTrue(String keyHash);
}
