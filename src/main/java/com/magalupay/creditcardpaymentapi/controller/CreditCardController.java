package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cartões de Crédito", description = "Endpoints para gerenciamento de cartões de crédito")
@RestController
@RequestMapping("/api/creditcards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Operation(summary = "Cadastra um novo cartão")
    @PostMapping
    public ResponseEntity<CreditCard> createCard(@RequestBody CreditCard card) {
        return ResponseEntity.status(201).body(creditCardService.saveCreditCard(card));
    }

    @Operation(summary = "Lista todos os cartões")
    @GetMapping
    public List<CreditCard> listCards() {
        return creditCardService.getAllCreditCards();
    }

    @Operation(summary = "Busca um cartão por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCard(@PathVariable Long id) {
        return creditCardService.getCreditCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove um cartão por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        creditCardService.deleteCreditCard(id);
        return ResponseEntity.noContent().build();
    }
}
