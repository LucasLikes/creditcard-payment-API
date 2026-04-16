package com.magalupay.creditcardpaymentapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalupay.creditcardpaymentapi.dto.TransactionDTO;
import com.magalupay.creditcardpaymentapi.model.Transaction;
import com.magalupay.creditcardpaymentapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionDTO transactionDTO;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO(1L, 100.0, "2023-01-01", 1L);
        transaction = Transaction.builder().id(1L).amount(100.0).date("2023-01-01").creditCardId(1L).build();
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(transactionService.fromDTO(any())).thenReturn(transaction);
        when(transactionService.saveTransaction(any())).thenReturn(transaction);
        when(transactionService.toDTO(any())).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testListTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(Arrays.asList(transaction));
        when(transactionService.toDTO(any())).thenReturn(transactionDTO);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransaction() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction));
        when(transactionService.toDTO(any())).thenReturn(transactionDTO);

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTransaction() throws Exception {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNoContent());
    }
}
