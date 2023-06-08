package com.upc.trabajoparejas.service;

import java.util.List;

import com.upc.trabajoparejas.model.Account;

public interface AccountService {
    public abstract Account createAccount(Account account);

    public abstract List<Account> getAllAccount();

    public abstract Account getAccountById(Long id);
}
