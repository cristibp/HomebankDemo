package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExposedEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetBankAccounts() throws Exception {
        mockMvc.perform(get("/list-bank-accounts"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("RO09BCYP0000001234567890")));
    }

    @Test
    public void testCreateBankAccountInvalidBankAccount() throws Exception {
        mockMvc.perform(post("/createBankAccount")
                .param("iban", "32"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("The provided IBAN 32 is not valid")));
    }

    @Test
    public void testCreateBankAccountValidBankAccount() throws Exception {
        mockMvc.perform(post("/createBankAccount")
                .param("iban", "123456789123456"))
                .andExpect(status().is3xxRedirection());
    }
}
