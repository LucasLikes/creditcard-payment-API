package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.dto.CreditCardDTO;
import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.service.CreditCardService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;

import java.util.List;

@Slf4j
@Tag(name = "Cartões de Crédito", description = "Endpoints para gerenciamento de cartões de crédito")
@RestController
@RequestMapping("/api/creditcards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Operation(summary = "Cadastra um novo cartão")
    @PostMapping
    public ResponseEntity<CreditCardDTO> createCard(@Valid @RequestBody CreditCardDTO cardDTO) {
        log.info("Creating credit card for holder: {}", cardDTO.getCardHolder());
        try {
            CreditCard card = creditCardService.fromDTO(cardDTO);
            CreditCard saved = creditCardService.saveCreditCard(card);
            log.info("Credit card created successfully with id: {}", saved.getId());
            return ResponseEntity.status(201).body(creditCardService.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating credit card", e);
            throw e;
        }
    }

    @Operation(summary = "Lista todos os cartões")
    @GetMapping
    public List<CreditCardDTO> listCards() {
        log.debug("Listing all credit cards");
        try {
            List<CreditCardDTO> cards = creditCardService.getAllCreditCards().stream().map(creditCardService::toDTO).toList();
            log.info("Retrieved {} credit cards", cards.size());
            return cards;
        } catch (Exception e) {
            log.error("Error listing credit cards", e);
            throw e;
        }
    }

    @Operation(summary = "Busca um cartão por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCard(@PathVariable Long id) {
        log.info("Fetching credit card with id: {}", id);
        MDC.put("cardId", id.toString());
        try {
            return creditCardService.getCreditCardById(id)
                    .map(creditCardService::toDTO)
                    .map(card -> {
                        log.info("Credit card found: {}", id);
                        return ResponseEntity.ok(card);
                    })
                    .orElseGet(() -> {
                        log.warn("Credit card not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching credit card with id: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Remove um cartão por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        log.info("Deleting credit card with id: {}", id);
        MDC.put("cardId", id.toString());
        try {
            creditCardService.deleteCreditCard(id);
            log.info("Credit card deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting credit card with id: {}", id, e);
            throw e;
        }
    }
}
