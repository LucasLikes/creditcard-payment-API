package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.dto.UserDTO;
import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.fromDTO(userDTO);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(201).body(userService.toDTO(savedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> dtos = users.stream()
                                  .map(userService::toDTO)
                                  .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
