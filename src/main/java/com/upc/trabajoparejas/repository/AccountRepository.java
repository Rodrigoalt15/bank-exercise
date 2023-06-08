package com.upc.trabajoparejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upc.trabajoparejas.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNameCustomer(String nameCustomer);

    boolean existsByNameCustomerAndNumberAccount(String nameCustomer, String NumberAccount);
}
