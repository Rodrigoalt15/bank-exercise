package com.upc.trabajoparejas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upc.trabajoparejas.exeption.ValidationException;
import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.model.Transaction;
import com.upc.trabajoparejas.service.AccountService;
import com.upc.trabajoparejas.service.TransactionService;

@RestController
@RequestMapping("/api/bank/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    // URL: http://localhost:8080/api/bank/v1/accounts/{id}/transactions
    // Method: POST
    @Transactional
    @PostMapping("/accounts/{id}/transactions")
    public ResponseEntity<Transaction> createTransaction(@PathVariable(value = "id") Long accounrId,
            @RequestBody Transaction transaction) {
        Account account = accountService.getAccountById(accounrId);
        ValidateTransaction(transaction);
        transaction.setCreateDate(LocalDate.now());
        transaction.setAccount(account);
        return new ResponseEntity<Transaction>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/bank/v1/transactions/filterByNameCustomer
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByNameCustomer")
    public ResponseEntity<List<Transaction>> filterByNameCustomer(
            @RequestParam(name = "nameCustomer") String nameCustomer) {
        return new ResponseEntity<List<Transaction>>(transactionService.getTransactionsByNameCustumer(nameCustomer),
                HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/bank/v1/transactions/filterByDateRange
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByDateRange")
    public ResponseEntity<List<Transaction>> filterByDateRange(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return new ResponseEntity<List<Transaction>>(
                transactionService.getTransactionsByDateRange(startDate, endDate),
                HttpStatus.OK);
    }

    private void ValidateTransaction(Transaction transaction) {
        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            throw new ValidationException("El tipo de transacción debe ser obligatorio");
        }
        if (transaction.getAmount() <= 0.0f) {
            throw new ValidationException("“El monto en una transacción bancaria debe ser mayor de S/.0.0");
        }
        if ("Retiro".equals(transaction.getType()) && transaction.getAmount() > transaction.getBalance()) {
            throw new ValidationException(
                    "En una transacción bancaria tipo retiro el monto no puede ser mayor al saldo");
        }
        if (!"Deposito".equals(transaction.getType()) && !"Retiro".equals(transaction.getType())) {
            throw new ValidationException("El tipo de transacción debe ser Deposito o Retiro");
        }
        if ("Deposito".equals(transaction.getType())) {
            transaction.setBalance(transaction.getBalance() + transaction.getAmount());
        }
        if ("Retiro".equals(transaction.getType()) && transaction.getAmount() <= transaction.getBalance()) {
            transaction.setBalance(transaction.getBalance() - transaction.getAmount());
        }
    }

}
