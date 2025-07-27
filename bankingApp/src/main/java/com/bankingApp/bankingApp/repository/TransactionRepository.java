package com.bankingApp.bankingApp.repository;

import com.bankingApp.bankingApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccount_Id(Long accountId);
    Transaction findTopByAccount_IdOrderByTimestampDesc(Long accountId);
}
