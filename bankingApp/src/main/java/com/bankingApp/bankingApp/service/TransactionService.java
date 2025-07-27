package com.bankingApp.bankingApp.service;

import com.bankingApp.bankingApp.entity.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getTransactionsByAccountId(Long accountId);
}
