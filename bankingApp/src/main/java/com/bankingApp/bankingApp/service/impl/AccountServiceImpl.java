package com.bankingApp.bankingApp.service.impl;

import com.bankingApp.bankingApp.Mapper.AccountMapper;
import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.repository.AccountRepository;
import com.bankingApp.bankingApp.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);


        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);


    }
}
