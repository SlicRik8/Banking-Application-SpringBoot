package com.bankingApp.bankingApp.Mapper;

import com.bankingApp.bankingApp.dto.RegisterRequest;
import com.bankingApp.bankingApp.entity.User;

public class UserMapper {
    public static User toUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password()); // We'll encode this in service
        return user;
    }


}
