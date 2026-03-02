package com.anudeep.apikey.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Hashing {
  public static String sha256(String s){
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] out = md.digest(s.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(out);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
}
