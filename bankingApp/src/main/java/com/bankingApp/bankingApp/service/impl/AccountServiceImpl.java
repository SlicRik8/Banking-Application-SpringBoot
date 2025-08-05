package com.bankingApp.bankingApp.service.impl;

import com.bankingApp.bankingApp.Exception.AccountException;
import com.bankingApp.bankingApp.Mapper.AccountMapper;
import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.dto.TransactionDto;
import com.bankingApp.bankingApp.dto.TransferDto;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.entity.User;
import com.bankingApp.bankingApp.repository.AccountRepository;
import com.bankingApp.bankingApp.repository.TransactionRepository;
import com.bankingApp.bankingApp.repository.UserRepository;
import com.bankingApp.bankingApp.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;


    public AccountServiceImpl(AccountRepository accountRepository,UserRepository userRepository,TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public AccountDto createAccount(String username,AccountDto accountDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));


        if(user.getAccount() != null){
            throw new RuntimeException("User already has an account");
        }

        Account account = AccountMapper.mapToAccount(accountDto);
        account.setUser(user);
        user.setAccount(account);



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

    public AccountDto getAccountByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    @Transactional
    public AccountDto deposit(TransactionDto request) {
        if(request.amount()<=0){
            throw new AccountException("Deposit amount must be greater than 0");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new AccountException("User not found") );

        Account account = user.getAccount();

        if(account == null){
            throw new AccountException("No account registered with the user");

        }
        account.setBalance(request.amount()+ account.getBalance());
        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(request.amount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return AccountMapper.mapToAccountDto(account);


    }

    @Override
    @Transactional
    public AccountDto withdraw(TransactionDto request) {
        if (request.amount() <= 0) {
            throw new AccountException("Withdraw amount must be greater than 0");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AccountException("User not found"));

        Account account = user.getAccount();
        if (account == null) {
            throw new AccountException("No account associated with the user");
        }

        if (account.getBalance() < request.amount()) {
            throw new AccountException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - request.amount());

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("WITHDRAW");
        transaction.setAmount(-request.amount());
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        accountRepository.save(account);



        return AccountMapper.mapToAccountDto(account);
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
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountException("Account does not exist"));

        User user = account.getUser();
        if(user!=null){
            user.setAccount(null); //break the relationship

        }



        accountRepository.delete(account);



    }

    @Transactional
    @Override
    public AccountDto transferMoney(TransferDto request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account fromAccount = user.getAccount();
        if (fromAccount == null) {
            throw new RuntimeException("No Account associated with the logged user");
        }

        if (fromAccount.getId().equals(request.toAccountId())) {
            throw new AccountException("Cannot transfer money to the same account");
        }

        if (request.amount() <= 0) {
            throw new AccountException("Transfer amount must be greater than 0");
        }

        Account toAccount = accountRepository.findById(request.toAccountId())
                .orElseThrow(() -> new AccountException("Destination Account Not Found"));

        if (fromAccount.getBalance() < request.amount()) {
            throw new AccountException("Insufficient funds");
        }

        double fromBalance = fromAccount.getBalance();
        double toBalance = toAccount.getBalance();
        double transferAmount = request.amount();

        fromAccount.setBalance(fromBalance - transferAmount);
        toAccount.setBalance(toBalance + transferAmount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setType("Transfer to account " + toAccount.getId());
        debitTransaction.setAmount(-transferAmount);
        debitTransaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setType("Transfer from account " + fromAccount.getId());
        creditTransaction.setAmount(transferAmount);
        creditTransaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(creditTransaction);

        AccountDto accountDto = AccountMapper.mapToAccountDto(fromAccount);
        return accountDto;
    }

}
