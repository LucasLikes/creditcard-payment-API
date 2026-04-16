package com.magalupay.creditcardpaymentapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalupay.creditcardpaymentapi.dto.UserDTO;
import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(1L, "John Doe", "john@example.com", new ArrayList<>());
        user = User.builder().id(1L).name("John Doe").email("john@example.com").creditCards(new ArrayList<>()).build();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.fromDTO(any())).thenReturn(user);
        when(userService.saveUser(any())).thenReturn(user);
        when(userService.toDTO(any())).thenReturn(userDTO);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testListUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
        when(userService.toDTO(any())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userService.toDTO(any())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
