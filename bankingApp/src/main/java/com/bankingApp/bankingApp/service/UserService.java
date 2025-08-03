package com.bankingApp.bankingApp.service;


import com.bankingApp.bankingApp.dto.LoginRequest;
import com.bankingApp.bankingApp.dto.LoginResponse;
import com.bankingApp.bankingApp.dto.RegisterRequest;
import com.bankingApp.bankingApp.entity.User;

public interface UserService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    User getByUsername(String username);
}
