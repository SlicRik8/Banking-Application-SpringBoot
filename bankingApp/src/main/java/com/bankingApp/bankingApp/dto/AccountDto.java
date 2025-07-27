package com.bankingApp.bankingApp.dto;


import com.bankingApp.bankingApp.entity.Transaction;

import java.util.List;

public record AccountDto(Long id, String accountHolderName, double balance, List<Transaction> transactions) {
}


