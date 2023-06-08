package com.upc.trabajoparejas.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.model.Transaction;
import com.upc.trabajoparejas.repository.AccountRepository;
import com.upc.trabajoparejas.repository.TransactionRepository;
import com.upc.trabajoparejas.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionReposity;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionReposity.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByNameCustumer(String nameCustomer) {
        List<Account> account = accountRepository.findByNameCustomer(nameCustomer);
        return transactionReposity.findByAccounts(account);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionReposity.findByCreateDateBetween(startDate, endDate);
    }

}
