package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.dto.TransactionDTO;
import com.magalupay.creditcardpaymentapi.model.Transaction;
import com.magalupay.creditcardpaymentapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction sampleTransaction;
    private TransactionDTO sampleTransactionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTransaction = Transaction.builder()
                .id(1L)
                .amount(new BigDecimal("100.50"))
                .timestamp(LocalDateTime.now())
                .build();

        sampleTransactionDTO = new TransactionDTO(1L, new BigDecimal("100.50"), LocalDateTime.now());
    }

    @Test
    void testSaveTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(sampleTransaction);
        Transaction saved = transactionService.saveTransaction(sampleTransaction);
        assertNotNull(saved);
        assertEquals(sampleTransaction.getId(), saved.getId());
        assertEquals(sampleTransaction.getAmount(), saved.getAmount());
        verify(transactionRepository, times(1)).save(sampleTransaction);
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> transactions = Arrays.asList(sampleTransaction);
        when(transactionRepository.findAll()).thenReturn(transactions);
        List<Transaction> result = transactionService.getAllTransactions();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransactionById_Found() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(sampleTransaction));
        Optional<Transaction> found = transactionService.getTransactionById(1L);
        assertTrue(found.isPresent());
        assertEquals(sampleTransaction.getAmount(), found.get().getAmount());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Transaction> found = transactionService.getTransactionById(999L);
        assertFalse(found.isPresent());
        verify(transactionRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(transactionRepository).deleteById(1L);
        transactionService.deleteTransaction(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testToDTO() {
        TransactionDTO dto = transactionService.toDTO(sampleTransaction);
        assertNotNull(dto);
        assertEquals(sampleTransaction.getId(), dto.getId());
        assertEquals(sampleTransaction.getAmount(), dto.getAmount());
    }

    @Test
    void testFromDTO() {
        Transaction tx = transactionService.fromDTO(sampleTransactionDTO);
        assertNotNull(tx);
        assertEquals(sampleTransactionDTO.getId(), tx.getId());
        assertEquals(sampleTransactionDTO.getAmount(), tx.getAmount());
    }

    @Test
    void testToDTONull() {
        TransactionDTO dto = transactionService.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testFromDTONull() {
        Transaction tx = transactionService.fromDTO(null);
        assertNull(tx);
    }
}
