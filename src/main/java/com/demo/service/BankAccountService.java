package com.demo.service;

import com.demo.exception.InvalidIBANException;
import com.demo.exception.NotWorkingDayException;
import com.demo.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    /**
     * Save a valid bank account
     *
     * @param bankAccount
     * @return
     */
    BankAccount save(BankAccount bankAccount) throws NotWorkingDayException, InvalidIBANException;

    /**
     * Retrieves the list of all the bank accounts associated
     * to the logged user
     *
     * @return
     */
    List<BankAccount> getBankAccounts();
}
