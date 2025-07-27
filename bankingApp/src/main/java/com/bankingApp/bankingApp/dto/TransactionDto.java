package com.bankingApp.bankingApp.dto;

import java.time.LocalDateTime;

public record TransactionDto(Long id,
                             String type,
                             double amount,
                             LocalDateTime timestamp) {

}
