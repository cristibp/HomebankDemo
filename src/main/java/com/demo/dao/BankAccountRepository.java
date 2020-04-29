package com.demo.dao;


import com.demo.model.BankAccount;
import com.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    List<BankAccount> findBankAccountsByUser(User user);
}
