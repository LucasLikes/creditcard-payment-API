package com.magalupay.creditcardpaymentapi.service;

import com.magalupay.creditcardpaymentapi.model.CreditCard;
import com.magalupay.creditcardpaymentapi.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    private CreditCard sampleCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCard = new CreditCard();
        sampleCard.setId(1L);
        sampleCard.setCardNumber("1234567890123456");
        sampleCard.setCardHolder("Lucas Likes");
        sampleCard.setExpirationDate("12/25");
        // User pode ser mockado se precisar
    }

    @Test
    void testSaveCreditCard() {
        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(sampleCard);

        CreditCard saved = creditCardService.saveCreditCard(sampleCard);

        assertNotNull(saved);
        assertEquals(sampleCard.getId(), saved.getId());
        verify(creditCardRepository, times(1)).save(sampleCard);
    }

    @Test
    void testGetAllCreditCards() {
        List<CreditCard> cardList = Arrays.asList(sampleCard);
        when(creditCardRepository.findAll()).thenReturn(cardList);

        List<CreditCard> result = creditCardService.getAllCreditCards();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(creditCardRepository, times(1)).findAll();
    }

    @Test
    void testGetCreditCardById_Found() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(sampleCard));

        Optional<CreditCard> found = creditCardService.getCreditCardById(1L);

        assertTrue(found.isPresent());
        assertEquals(sampleCard.getCardNumber(), found.get().getCardNumber());
        verify(creditCardRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCreditCardById_NotFound() {
        when(creditCardRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<CreditCard> found = creditCardService.getCreditCardById(2L);

        assertFalse(found.isPresent());
        verify(creditCardRepository, times(1)).findById(2L);
    }

    @Test
    void testDeleteCreditCard() {
        doNothing().when(creditCardRepository).deleteById(1L);

        creditCardService.deleteCreditCard(1L);

        verify(creditCardRepository, times(1)).deleteById(1L);
    }
}
