package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard saveCreditCard(CreditCard card) {
        log.info("Saving credit card: {}", card);

        return creditCardRepository.save(card);
    }

    public List<CreditCard> getAllCreditCards() {
        log.info("Fetching all credit cards");

        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> getCreditCardById(Long id) {
        log.info("Fetching credit card by id: {}", id);

        return creditCardRepository.findById(id);
    }

    public void deleteCreditCard(Long id) {
        log.info("Deleting credit card by id: {}", id);

        creditCardRepository.deleteById(id);
    }

        public com.magalupay.creditcardpaymentapi.dto.CreditCardDTO toDTO(CreditCard card) {
        if (card == null) return null;
        return new com.magalupay.creditcardpaymentapi.dto.CreditCardDTO(
            card.getId(),
            card.getCardNumber(),
            card.getCardHolder(),
            card.getExpirationDate()
        );
    }

    public CreditCard fromDTO(com.magalupay.creditcardpaymentapi.dto.CreditCardDTO dto) {
        if (dto == null) return null;
        return CreditCard.builder()
            .id(dto.getId())
            .cardNumber(dto.getCardNumber())
            .cardHolder(dto.getCardHolder())
            .expirationDate(dto.getExpirationDate())
            .build();
    }

// DTO conversion methods
    public com.magalupay.creditcardpaymentapi.dto.CreditCardDTO toDTO(CreditCard card) {
        if (card == null) return null;
        com.magalupay.creditcardpaymentapi.dto.CreditCardDTO dto = new com.magalupay.creditcardpaymentapi.dto.CreditCardDTO();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setCardHolder(card.getCardHolder());
        dto.setExpirationDate(card.getExpirationDate());
        return dto;
    }

    public CreditCard fromDTO(com.magalupay.creditcardpaymentapi.dto.CreditCardDTO dto) {
        if (dto == null) return null;
        CreditCard card = new CreditCard();
        card.setId(dto.getId());
        card.setCardNumber(dto.getCardNumber());
        card.setCardHolder(dto.getCardHolder());
        card.setExpirationDate(dto.getExpirationDate());
        return card;
    }
}
