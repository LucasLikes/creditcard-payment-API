package com.magalupay.creditcardpaymentapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_cards")
@Data // já inclui @Getter, @Setter, @ToString, @EqualsAndHashCode e @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolder;

    @Column(nullable = false)
    private String expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
