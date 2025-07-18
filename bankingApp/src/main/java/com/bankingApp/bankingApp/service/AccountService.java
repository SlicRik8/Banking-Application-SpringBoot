package com.bankingApp.bankingApp.service;

import com.bankingApp.bankingApp.dto.AccountDto;

import com.bankingApp.bankingApp.repository.AccountRepository;

public interface AccountService {


    AccountDto createAccount(AccountDto accountDto);
}
