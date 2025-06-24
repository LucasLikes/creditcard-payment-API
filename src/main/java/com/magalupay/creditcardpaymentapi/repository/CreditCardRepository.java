package com.magalupay.creditcardpaymentapi.repository;

import com.magalupay.creditcardpaymentapi.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {}
