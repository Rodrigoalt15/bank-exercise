package com.upc.trabajoparejas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upc.trabajoparejas.exeption.ValidationException;
import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.repository.AccountRepository;
import com.upc.trabajoparejas.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/bank/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // URL: http://localhost:8080/api/bank/v1/accounts
    // Method: POST
    @Transactional
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        validateAccount(account);
        existsByNameCustomerAndNumberAccount(account);
        return new ResponseEntity<Account>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/bank/v1/accounts
    // Method: GET
    @Transactional(readOnly = true)
    @RequestMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccount() {
        return new ResponseEntity<List<Account>>(accountService.getAllAccount(), HttpStatus.OK);
    }

    private void validateAccount(Account account) {
        if (account.getNameCustomer() == null || account.getNameCustomer().isEmpty()) {
            throw new ValidationException("El nombre del cliente debe ser obligatorio");
        }
        if (account.getNameCustomer().length() > 30) {
            throw new ValidationException("“El nombre del cliente no debe exceder los 30 caracteres");
        }
        if (account.getNumberAccount() == null || account.getNumberAccount().isEmpty()) {
            throw new ValidationException("El número de cuenta debe ser obligatorio");
        }
        if (account.getNumberAccount().length() != 13) {
            throw new ValidationException("“El número de cuenta debe tener una longitud de 13 caracteres");
        }

    }

    private void existsByNameCustomerAndNumberAccount(Account account) {
        if (accountRepository.existsByNameCustomerAndNumberAccount(account.getNameCustomer(),
                account.getNumberAccount())) {
            throw new ValidationException("“No se puede registrar la cuenta porque ya existe uno con estos datos");
        }
    }
}
