package com.magalupay.creditcardpaymentapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalupay.creditcardpaymentapi.dto.CreditCardDTO;
import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.service.CreditCardService;
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
class CreditCardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreditCardDTO creditCardDTO;
    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        creditCardDTO = new CreditCardDTO(1L, "1234567890123456", "12/25", "123", 1L, new ArrayList<>());
        creditCard = CreditCard.builder().id(1L).number("1234567890123456").expiry("12/25").cvv("123").userId(1L).transactions(new ArrayList<>()).build();
    }

    @Test
    void testCreateCreditCard() throws Exception {
        when(creditCardService.fromDTO(any())).thenReturn(creditCard);
        when(creditCardService.saveCreditCard(any())).thenReturn(creditCard);
        when(creditCardService.toDTO(any())).thenReturn(creditCardDTO);

        mockMvc.perform(post("/api/creditcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditCardDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testListCreditCards() throws Exception {
        when(creditCardService.getAllCreditCards()).thenReturn(Arrays.asList(creditCard));
        when(creditCardService.toDTO(any())).thenReturn(creditCardDTO);

        mockMvc.perform(get("/api/creditcards"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCreditCard() throws Exception {
        when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.of(creditCard));
        when(creditCardService.toDTO(any())).thenReturn(creditCardDTO);

        mockMvc.perform(get("/api/creditcards/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCreditCard() throws Exception {
        doNothing().when(creditCardService).deleteCreditCard(1L);

        mockMvc.perform(delete("/api/creditcards/1"))
                .andExpect(status().isNoContent());
    }
}
