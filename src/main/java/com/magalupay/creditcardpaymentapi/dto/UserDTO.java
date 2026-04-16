package com.magalupay.creditcardpaymentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @jakarta.validation.constraints.NotBlank
    private String name;

    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.NotBlank
    private String email;

    private List<CreditCardDTO> creditCards;
}
