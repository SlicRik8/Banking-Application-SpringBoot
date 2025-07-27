package com.bankingApp.bankingApp.controller;

import com.bankingApp.bankingApp.entity.Transaction;
import com.bankingApp.bankingApp.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping("/account/{id}")
    public ResponseEntity<List<Transaction>> getTransactionByAccount(@PathVariable Long id){
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(id);
        return ResponseEntity.ok(transactions);

    }
}
