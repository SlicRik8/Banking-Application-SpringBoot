package com.bankingApp.bankingApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    private double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Account(Long id, String accountHolderName, double balance, List<Transaction> transactions) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactions = transactions;
    }
}
