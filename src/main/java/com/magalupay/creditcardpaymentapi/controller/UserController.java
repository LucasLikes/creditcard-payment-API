package com.magalupay.creditcardpaymentapi.controller;

import com.magalupay.creditcardpaymentapi.dto.UserDTO;
import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.MDC;

import java.util.List;

@Slf4j
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating user with email: {}", userDTO.getEmail());
        try {
            User user = userService.fromDTO(userDTO);
            User savedUser = userService.saveUser(user);
            log.info("User created successfully with id: {}", savedUser.getId());
            MDC.put("userId", savedUser.getId().toString());
            return ResponseEntity.status(201).body(userService.toDTO(savedUser));
        } catch (Exception e) {
            log.error("Error creating user with email: {}", userDTO.getEmail(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        log.debug("Listing all users");
        try {
            List<User> users = userService.getAllUsers();
            List<UserDTO> dtos = users.stream()
                                      .map(userService::toDTO)
                                      .toList();
            log.info("Retrieved {} users", users.size());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error listing users", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        MDC.put("userId", id.toString());
        try {
            return userService.getUserById(id)
                    .map(userService::toDTO)
                    .map(user -> {
                        log.info("User found: {}", id);
                        return ResponseEntity.ok(user);
                    })
                    .orElseGet(() -> {
                        log.warn("User not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching user with id: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        MDC.put("userId", id.toString());
        try {
            userService.deleteUser(id);
            log.info("User deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting user with id: {}", id, e);
            throw e;
        }
    }
}
