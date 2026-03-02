package com.anudeep.apikey.security;

import com.anudeep.apikey.model.Customer;
import com.anudeep.apikey.repo.ApiKeyRepo;
import com.anudeep.apikey.repo.CustomerRepo;
import com.anudeep.apikey.model.ApiKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
  private final ApiKeyRepo apiKeyRepo;
  private final RateLimiter rateLimiter;

  public ApiKeyAuthFilter(ApiKeyRepo apiKeyRepo, RateLimiter rateLimiter){
    this.apiKeyRepo = apiKeyRepo;
    this.rateLimiter = rateLimiter;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String p = request.getRequestURI();
    return p.startsWith("/auth") || p.startsWith("/swagger") || p.startsWith("/v3/api-docs") || p.equals("/health");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String raw = request.getHeader("X-API-Key");
    if (raw == null || raw.isBlank()){
      response.setStatus(401);
      response.getWriter().write("Missing X-API-Key");
      return;
    }
    String hash = Hashing.sha256(raw);
    Optional<ApiKey> k = apiKeyRepo.findByKeyHashAndActiveTrue(hash);
    if (k.isEmpty()){
      response.setStatus(403);
      response.getWriter().write("Invalid API key");
      return;
    }
    // Rate limit by key hash
    if (!rateLimiter.allow(hash)){
      response.setStatus(429);
      response.getWriter().write("Rate limit exceeded");
      return;
    }
    filterChain.doFilter(request, response);
  }
}
