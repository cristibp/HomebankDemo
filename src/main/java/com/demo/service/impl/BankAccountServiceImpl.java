package com.demo.service.impl;

import com.demo.dao.BankAccountRepository;
import com.demo.dao.UserRepository;
import com.demo.exception.DuplicateAccountException;
import com.demo.exception.InvalidIBANException;
import com.demo.exception.NotWorkingDayException;
import com.demo.model.BankAccount;
import com.demo.model.User;
import com.demo.service.BankAccountService;
import com.demo.service.OpenTableService;
import com.demo.validator.IBANValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final OpenTableService openTableService;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository, OpenTableService openTableService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.openTableService = openTableService;
    }


    /**
     * @see BankAccountService#save(BankAccount)
     */
    @Override
    public BankAccount save(BankAccount bankAccount) throws NotWorkingDayException, InvalidIBANException, DuplicateAccountException {
        if (!openTableService.isWorkingDay(LocalDateTime.now())) {
            throw new NotWorkingDayException("The action has to be performed between working hours");
        }
        if(!bankAccountRepository.findBankAccountsByUser(getLoggedUser()).isEmpty()) {
            throw new DuplicateAccountException("Only one account per user is accepted");
        }
        IBANValidator ibanValidator = new IBANValidator(15);
        String iban = bankAccount.getIban();
        if (!ibanValidator.isValid(iban)) {
            throw new InvalidIBANException(String.format("The provided IBAN %s is not valid", iban));
        }
        bankAccount.setUser(getLoggedUser());

        return bankAccountRepository.save(bankAccount);
    }

    /**
     * @see BankAccountService#getBankAccounts()
     */
    @Override
    public List<BankAccount> getBankAccounts() {
        User loggedUser = getLoggedUser();

        return bankAccountRepository.findBankAccountsByUser(loggedUser);
    }

    private User getLoggedUser() {
        return userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
