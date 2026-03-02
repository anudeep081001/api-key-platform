package com.anudeep.apikey.security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RateLimiter {
  private final StringRedisTemplate redis;
  private final int capacity = Integer.parseInt(System.getenv().getOrDefault("RL_CAPACITY","10"));
  private final double refillPerSec = Double.parseDouble(System.getenv().getOrDefault("RL_REFILL_PER_SEC","2"));

  public RateLimiter(StringRedisTemplate redis){
    this.redis = redis;
  }

  // Token bucket in Redis using hash fields: tokens, lastTs
  public boolean allow(String key){
    String hkey = "rl:" + key;
    long now = Instant.now().toEpochMilli();
    String tokensStr = redis.opsForHash().get(hkey,"tokens") != null ? redis.opsForHash().get(hkey,"tokens").toString() : null;
    String lastStr = redis.opsForHash().get(hkey,"last") != null ? redis.opsForHash().get(hkey,"last").toString() : null;

    double tokens = tokensStr == null ? capacity : Double.parseDouble(tokensStr);
    long last = lastStr == null ? now : Long.parseLong(lastStr);

    double refill = ((now - last)/1000.0) * refillPerSec;
    tokens = Math.min(capacity, tokens + refill);

    boolean allowed = tokens >= 1.0;
    if (allowed) tokens -= 1.0;

    redis.opsForHash().put(hkey,"tokens", Double.toString(tokens));
    redis.opsForHash().put(hkey,"last", Long.toString(now));
    redis.expire(hkey, java.time.Duration.ofMinutes(30));
    return allowed;
  }
}
