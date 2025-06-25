package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.dto.CreditCardDTO;
import com.magalupay.creditcardpaymentapi.dto.UserDTO;
import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        // Validações aqui se quiser
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO toDTO(User user) {
        if (user == null)
            return null;

        List<CreditCardDTO> cards = user.getCreditCards() == null ? List.of()
                : user.getCreditCards().stream()
                        .map(card -> new CreditCardDTO(
                                card.getId(),
                                card.getCardNumber(),
                                card.getCardHolder(),
                                card.getExpirationDate()))
                        .toList();

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), cards);
    }

    public User fromDTO(UserDTO dto) {
        if (dto == null)
            return null;

        List<CreditCard> cards = dto.getCreditCards() == null ? List.of()
                : dto.getCreditCards().stream()
                        .map(cardDto -> CreditCard.builder()
                                .id(cardDto.getId())
                                .cardNumber(cardDto.getCardNumber())
                                .cardHolder(cardDto.getCardHolder())
                                .expirationDate(cardDto.getExpirationDate())
                                .build())
                        .toList();

        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .creditCards(cards)
                .build();
    }
}
