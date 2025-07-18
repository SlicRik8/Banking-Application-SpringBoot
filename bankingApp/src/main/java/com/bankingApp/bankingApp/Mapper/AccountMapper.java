package com.bankingApp.bankingApp.Mapper;

import com.bankingApp.bankingApp.dto.AccountDto;
import com.bankingApp.bankingApp.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        //account dto to an account jpa entity
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
        return  account;
    }
    public  static AccountDto mapToAccountDto(Account account){
        //acount to dto
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
