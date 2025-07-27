package com.bankingApp.bankingApp.service;

import com.bankingApp.bankingApp.dto.AccountDto;

import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.repository.AccountRepository;

import java.util.List;

public interface AccountService {




    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id,double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

}
