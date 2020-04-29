package com.demo;

import com.demo.exception.DuplicateAccountException;
import com.demo.exception.InvalidIBANException;
import com.demo.exception.NotWorkingDayException;
import com.demo.model.BankAccount;
import com.demo.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExposedEndpointTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BankAccountService bankAccountService;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback
    public void testGetBankAccounts() throws Exception {
        aBankAccount();

        mockMvc.perform(get("/list-bank-accounts"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("123456789123456")));
    }

    @Test
    public void testCreateBankAccountInvalidBankAccount() throws Exception {
        mockMvc.perform(post("/createBankAccount")
                .param("iban", "32"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("The provided IBAN 32 is not valid")));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateBankAccountValidBankAccount() throws Exception {
        mockMvc.perform(post("/createBankAccount")
                .param("iban", "123456789123456"))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @Transactional
    @Rollback
    public void testCreateBankAccountValidBankAccountWhenOneAlreadyExists() throws Exception {
        aBankAccount();

        mockMvc.perform(post("/createBankAccount")
                .param("iban", "123456789123456"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Only one account per user is accepted")));
    }

    private void aBankAccount() throws NotWorkingDayException, InvalidIBANException, DuplicateAccountException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("123456789123456");
        bankAccountService.save(bankAccount);
    }
}
