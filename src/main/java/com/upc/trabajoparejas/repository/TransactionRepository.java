package com.upc.trabajoparejas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account IN :accounts")
    List<Transaction> findByAccounts(@Param("accounts") List<Account> account);
}
