package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.dto.TransactionDTO;
import com.magalupay.creditcardpaymentapi.model.Transaction;
import com.magalupay.creditcardpaymentapi.service.TransactionService;
io.swagger.v3.oas.annotations.Operation;
io.swagger.v3.oas.annotations.tags.Tag;
lombok.RequiredArgsConstructor;
lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import java.util.List;

@Slf4j
@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Cria uma nova transação")
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        log.info("Creating transaction with amount: {}", transactionDTO.getAmount());
        try {
            Transaction transaction = transactionService.fromDTO(transactionDTO);
            Transaction saved = transactionService.saveTransaction(transaction);
            log.info("Transaction created successfully with id: {}", saved.getId());
            return ResponseEntity.status(201).body(transactionService.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating transaction", e);
            throw e;
        }
    }

    @Operation(summary = "Lista todas as transações")
    @GetMapping
    public List<TransactionDTO> listTransactions() {
        log.debug("Listing all transactions");
        try {
            List<TransactionDTO> transactions = transactionService.getAllTransactions().stream()
                    .map(transactionService::toDTO).toList();
            log.info("Retrieved {} transactions", transactions.size());
            return transactions;
        } catch (Exception e) {
            log.error("Error listing transactions", e);
            throw e;
        }
    }

    @Operation(summary = "Busca uma transação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        log.info("Fetching transaction with id: {}", id);
        MDC.put("transactionId", id.toString());
        try {
            return transactionService.getTransactionById(id)
                    .map(transactionService::toDTO)
                    .map(tx -> {
                        log.info("Transaction found: {}", id);
                        return ResponseEntity.ok(tx);
                    })
                    .orElseGet(() -> {
                        log.warn("Transaction not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching transaction with id: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Exclui uma transação por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.info("Deleting transaction with id: {}", id);
        MDC.put("transactionId", id.toString());
        try {
            transactionService.deleteTransaction(id);
            log.info("Transaction deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting transaction with id: {}", id, e);
            throw e;
        }
    }
}


import com.magalupay.creditcardpaymentapi.dto.TransactionDTO;
import com.magalupay.creditcardpaymentapi.model.Transaction;
import com.magalupay.creditcardpaymentapi.service.TransactionService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Cria uma nova transação")
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.fromDTO(transactionDTO);
        Transaction saved = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(201).body(transactionService.toDTO(saved));
    }

    @Operation(summary = "Lista todas as transações")
    @GetMapping
    public List<TransactionDTO> listTransactions() {
        return transactionService.getAllTransactions().stream().map(transactionService::toDTO).toList();
    }

    @Operation(summary = "Busca uma transação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transactionService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui uma transação por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
