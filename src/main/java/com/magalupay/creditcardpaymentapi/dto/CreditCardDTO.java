package com.magalupay.creditcardpaymentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
}
