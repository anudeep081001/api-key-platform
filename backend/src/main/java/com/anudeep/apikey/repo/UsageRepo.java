package com.anudeep.apikey.repo;

import com.anudeep.apikey.model.UsageEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepo extends JpaRepository<UsageEvent, Long> {}
