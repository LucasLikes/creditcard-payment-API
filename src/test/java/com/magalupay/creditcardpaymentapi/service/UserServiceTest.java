package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.dto.CreditCardDTO;
import com.magalupay.creditcardpaymentapi.dto.UserDTO;
import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.model.User;
import com.magalupay.creditcardpaymentapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private UserDTO sampleUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .creditCards(new ArrayList<>())
                .build();

        sampleUserDTO = new UserDTO(1L, "John Doe", "john@example.com", new ArrayList<>());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        User saved = userService.saveUser(sampleUser);
        assertNotNull(saved);
        assertEquals(sampleUser.getId(), saved.getId());
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        Optional<User> found = userService.getUserById(1L);
        assertTrue(found.isPresent());
        assertEquals(sampleUser.getEmail(), found.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<User> found = userService.getUserById(999L);
        assertFalse(found.isPresent());
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testToDTO() {
        UserDTO dto = userService.toDTO(sampleUser);
        assertNotNull(dto);
        assertEquals(sampleUser.getId(), dto.getId());
        assertEquals(sampleUser.getName(), dto.getName());
        assertEquals(sampleUser.getEmail(), dto.getEmail());
    }

    @Test
    void testFromDTO() {
        User user = userService.fromDTO(sampleUserDTO);
        assertNotNull(user);
        assertEquals(sampleUserDTO.getId(), user.getId());
        assertEquals(sampleUserDTO.getName(), user.getName());
        assertEquals(sampleUserDTO.getEmail(), user.getEmail());
    }

    @Test
    void testToDTONull() {
        UserDTO dto = userService.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testFromDTONull() {
        User user = userService.fromDTO(null);
        assertNull(user);
    }
}
