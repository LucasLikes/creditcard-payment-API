package com.magalupay.creditcardpaymentapi.repository;

import com.magalupay.creditcardpaymentapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
