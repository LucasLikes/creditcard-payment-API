package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.saveUser(user));
    }

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Busca um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui um usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
