package com.bankingApp.bankingApp.service.impl;

import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.repository.TransactionRepository;
import com.bankingApp.bankingApp.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    public List<Transaction> getTransactionsByAccountId(Long accountId){
        return transactionRepository.findByAccount_Id(accountId);
    }
}
