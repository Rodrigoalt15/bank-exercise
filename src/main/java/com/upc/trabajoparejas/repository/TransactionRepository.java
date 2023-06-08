package com.upc.trabajoparejas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    List<Transaction> findByAccount(Account account);
}
