package com.lqd.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class HashPassword {
   public String hashPassword(String password) {
       try {
           MessageDigest digest = MessageDigest.getInstance("SHA-256");
           byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
           String hashedPassword = Base64.getEncoder().encodeToString(hash);
           return hashedPassword;
       } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException("Error hashing password", e);
       }
   }
}



