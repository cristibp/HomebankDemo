package com.demo.controller;

import com.demo.dto.BankAccountDTO;
import com.demo.exception.InvalidIBANException;
import com.demo.exception.NotWorkingDayException;
import com.demo.model.BankAccount;
import com.demo.service.BankAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, ModelMapper modelMapper) {
        this.bankAccountService = bankAccountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/list-bank-accounts")
    public String listBankAccounts(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
        model.addAttribute("bankAccounts", bankAccounts.stream()
                .map(bankAccount -> modelMapper.map(bankAccount, BankAccountDTO.class))
                .collect(Collectors.toList()));

        return "list-bank-accounts";
    }

    @GetMapping("/add-bank-account")
    public String addBankAccount(@ModelAttribute("bankAccount") BankAccountDTO bankAccountDTOParam, BindingResult errors, Model model) {
        return "add-bank-account";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", "Cristi");

        return "welcome";
    }

    @PostMapping("/createBankAccount")
    public String createBankAccountWeb(@Valid @ModelAttribute("bankAccount") BankAccountDTO bankAccountDTO, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "add-bank-account";
        }
        BankAccount bankAccount = modelMapper.map(bankAccountDTO, BankAccount.class);
        try {
            bankAccountService.save(bankAccount);
        } catch (NotWorkingDayException e) {
            errors.addError(new ObjectError("not_working_day", e.getMessage()));
            return "add-bank-account";
        } catch (InvalidIBANException e) {
            errors.addError(new FieldError("bankAccount","iban", e.getMessage()));
            return "add-bank-account";
        }

        return "redirect:/list-bank-accounts";
    }
}
