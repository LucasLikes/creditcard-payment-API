package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard saveCreditCard(CreditCard card) {
        return creditCardRepository.save(card);
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> getCreditCardById(Long id) {
        return creditCardRepository.findById(id);
    }

    public void deleteCreditCard(Long id) {
        creditCardRepository.deleteById(id);
    }
}
