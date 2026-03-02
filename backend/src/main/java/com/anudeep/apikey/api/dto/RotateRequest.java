package com.anudeep.apikey.api.dto;

import jakarta.validation.constraints.NotBlank;

public class RotateRequest {
  @NotBlank
  public String apiKey;
}
