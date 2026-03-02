package com.anudeep.apikey.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
  @Email @NotBlank
  public String email;
}
