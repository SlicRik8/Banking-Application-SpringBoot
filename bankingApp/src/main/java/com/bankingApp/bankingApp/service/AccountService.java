package com.bankingApp.bankingApp.service;

import com.bankingApp.bankingApp.dto.AccountDto;

import com.bankingApp.bankingApp.dto.TransactionDto;
import com.bankingApp.bankingApp.dto.TransferDto;
import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.repository.AccountRepository;

import java.util.List;

public interface AccountService {




    AccountDto createAccount(String username,AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(TransactionDto request);

    AccountDto withdraw(TransactionDto request);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);
    AccountDto transferMoney(TransferDto request);

}
