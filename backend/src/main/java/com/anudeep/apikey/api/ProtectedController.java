package com.anudeep.apikey.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
  @GetMapping("/ping")
  public Object ping(){
    return java.util.Map.of("pong", true, "ts", java.time.Instant.now().toString());
  }
}
