package com.bankingApp.bankingApp.controller;


import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.dto.TransactionDto;
import com.bankingApp.bankingApp.dto.TransferDto;
import com.bankingApp.bankingApp.entity.Account;
import com.bankingApp.bankingApp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts =  accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //add account rest api
    @PostMapping("/create")
    public ResponseEntity<AccountDto> addAccount(@RequestParam String username, @RequestBody AccountDto accountDto){
//        System.out.println("AccountHolderName: " + accountDto.accountHolderName());
//        System.out.println("Balance: " + accountDto.balance());
        return new ResponseEntity<>(accountService.createAccount(username,accountDto), HttpStatus.CREATED);
    }

    //deposit rest api
    @PutMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestBody TransactionDto request){



        AccountDto accountDto = accountService.deposit(request);



        return ResponseEntity.ok(accountDto);


    }


    // withdraw rest api
    @PutMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestBody TransactionDto request){


        AccountDto accountDto = accountService.withdraw(request);

        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountDto> transferMoney(@RequestBody TransferDto transferDto){
        AccountDto accountDto = accountService.transferMoney(transferDto);

        return ResponseEntity.ok(accountDto);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        accountService.deleteAccount(id);
        return  ResponseEntity.ok("Account deletion successful");
    }

}
