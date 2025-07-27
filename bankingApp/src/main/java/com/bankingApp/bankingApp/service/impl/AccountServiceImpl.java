package com.bankingApp.bankingApp.service.impl;

import com.bankingApp.bankingApp.Exception.AccountException;
import com.bankingApp.bankingApp.Mapper.AccountMapper;
import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.repository.AccountRepository;
import com.bankingApp.bankingApp.repository.TransactionRepository;
import com.bankingApp.bankingApp.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;


    public AccountServiceImpl(AccountRepository accountRepository,TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);


        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);


    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        return AccountMapper.mapToAccountDto(account);

    }

    @Override
    @Transactional
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
        double total = account.getBalance() + amount;
        account.setBalance(total);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        //save the transaction
        transactionRepository.save(transaction);

        //save it in db
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    @Transactional
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        if (amount > account.getBalance()) {
            throw new RuntimeException("Insufficient Balance");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();

        //converting the entity accountlist to account dto using account mapper and then returning the dto list
        return accountList.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
        accountRepository.delete(account);


    }
}
