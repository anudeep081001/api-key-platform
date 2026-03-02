package com.anudeep.apikey.api;

import com.anudeep.apikey.api.dto.KeyIssueRequest;
import com.anudeep.apikey.api.dto.RotateRequest;
import com.anudeep.apikey.service.KeyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class KeysController {
  private final KeyService keyService;
  public KeysController(KeyService keyService){ this.keyService = keyService; }

  @PostMapping("/keys")
  public Object issue(@Valid @RequestBody KeyIssueRequest req){
    var out = keyService.issueKey(req.email);
    return java.util.Map.of("customerId", out.customerId(), "apiKey", out.apiKey());
  }

  @PostMapping("/keys/rotate")
  public Object rotate(@Valid @RequestBody RotateRequest req){
    var out = keyService.rotate(req.apiKey);
    return java.util.Map.of("customerId", out.customerId(), "apiKey", out.apiKey());
  }

  @GetMapping("/health")
  public Object health(){ return java.util.Map.of("ok", true); }
}
