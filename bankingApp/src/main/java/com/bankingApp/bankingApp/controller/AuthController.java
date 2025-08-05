package com.bankingApp.bankingApp.controller;

import com.bankingApp.bankingApp.Mapper.AccountMapper;
import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.dto.LoginRequest;
import com.bankingApp.bankingApp.dto.LoginResponse;
import com.bankingApp.bankingApp.dto.RegisterRequest;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.entity.User;
import com.bankingApp.bankingApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse loginResponse = userService.login(request);
        return ResponseEntity.ok(loginResponse);


    }

}
