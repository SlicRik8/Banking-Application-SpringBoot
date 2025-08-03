package com.bankingApp.bankingApp.service.impl;

import com.bankingApp.bankingApp.Mapper.AccountMapper;
import com.bankingApp.bankingApp.Mapper.UserMapper;
import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.dto.LoginRequest;
import com.bankingApp.bankingApp.dto.LoginResponse;
import com.bankingApp.bankingApp.dto.RegisterRequest;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.entity.User;
import com.bankingApp.bankingApp.repository.UserRepository;
import com.bankingApp.bankingApp.service.JwtService;
import com.bankingApp.bankingApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;






    public void register(RegisterRequest request){
        if(userRepository.findByUsername(request.username()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        //encrypt password
        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Account account = new Account();
        account.setAccountHolderName(request.username());
        account.setBalance(0.0); // default balance
        account.setUser(user);

        user.setAccount(account);

        userRepository.save(user);

    }
    public LoginResponse login(LoginRequest request){
        Optional<User> optionalUser = userRepository.findByUsername(request.username());

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        User user = optionalUser.get();

        //check if password matches
        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        String token =  jwtService.generateToken(user);

        AccountDto accountDto = AccountMapper.mapToAccountDto(user.getAccount());

        return new LoginResponse(token,user.getId(),
                user.getUsername(),accountDto);


    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
