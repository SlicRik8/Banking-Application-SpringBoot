package com.bankingApp.bankingApp.dto;

public record LoginResponse(String token,
                            Long userId,
                            String username,
                            AccountDto account) {
}
