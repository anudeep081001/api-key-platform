package com.anudeep.apikey.api;

import com.anudeep.apikey.api.dto.RegisterRequest;
import com.anudeep.apikey.service.KeyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final KeyService keyService;
  public AuthController(KeyService keyService){ this.keyService = keyService; }

  @PostMapping("/register")
  public Object register(@Valid @RequestBody RegisterRequest req){
    var c = keyService.getOrCreateCustomer(req.email);
    return java.util.Map.of("customerId", c.getId(), "email", c.getEmail());
  }
}
