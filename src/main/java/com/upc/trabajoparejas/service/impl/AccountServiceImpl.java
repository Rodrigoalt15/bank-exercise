package com.upc.trabajoparejas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upc.trabajoparejas.exeption.ResourceNotFoundException;
import com.upc.trabajoparejas.model.Account;
import com.upc.trabajoparejas.repository.AccountRepository;
import com.upc.trabajoparejas.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la cuenta con el id: " + id));
    }
}
