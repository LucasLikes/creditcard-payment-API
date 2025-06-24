package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.model.Transaction;
import com.magalupay.creditcardpaymentapi.service.TransactionService;
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
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.status(201).body(transactionService.saveTransaction(transaction));
    }

    @Operation(summary = "Lista todas as transações")
    @GetMapping
    public List<Transaction> listTransactions() {
        return transactionService.getAllTransactions();
    }

    @Operation(summary = "Busca uma transação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
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
