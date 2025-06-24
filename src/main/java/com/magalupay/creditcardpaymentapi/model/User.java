package com.magalupay.creditcardpaymentapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Relacionamento com CreditCards (OneToMany)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards;

    // Construtores
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<CreditCard> getCreditCards() { return creditCards; }
    public void setCreditCards(List<CreditCard> creditCards) { this.creditCards = creditCards; }
}
