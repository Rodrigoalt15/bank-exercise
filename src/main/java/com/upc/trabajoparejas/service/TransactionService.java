package com.upc.trabajoparejas.service;

import java.time.LocalDate;
import java.util.List;

import com.upc.trabajoparejas.model.Transaction;

public interface TransactionService {
    public abstract Transaction createTransaction(Transaction transaction);

    public abstract List<Transaction> getTransactionsByNameCustumer(String nameCustomer);

    public abstract List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate);

}
