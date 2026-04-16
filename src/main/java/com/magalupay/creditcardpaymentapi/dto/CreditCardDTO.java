package com.magalupay.creditcardpaymentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {
    private Long id;

    @jakarta.validation.constraints.NotBlank
    private String cardNumber;

    @jakarta.validation.constraints.NotBlank
    private String cardHolder;

    @jakarta.validation.constraints.NotBlank
    private String expirationDate;
}
