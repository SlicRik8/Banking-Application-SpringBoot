package com.bankingApp.bankingApp.service;

import com.bankingApp.bankingApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


public interface JwtService {
    String generateToken(User user);
    boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);


}
