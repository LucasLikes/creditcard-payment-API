package com.magalupay.creditcardpaymentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovedEvent {

    private String transactionId;

    private BigDecimal amount;

    private String timestamp;
}
