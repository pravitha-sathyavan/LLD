package org.example.stockdatamonitoring.service;


import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.domain.UserSymbol;
import org.example.stockdatamonitoring.exception.InvalidSubscription;
import org.example.stockdatamonitoring.exception.UserNotFoundException;
import org.example.stockdatamonitoring.repository.SymbolRepository;
import org.example.stockdatamonitoring.repository.UserSymbolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SymbolServiceTest {

    @Mock
    private SymbolRepository symbolRepository;

    @Mock
    private UserSymbolRepository userSymbolRepository;

    @InjectMocks
    private SymbolService symbolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubscribe_NewSymbol() throws UserNotFoundException {
        Symbols symbol = new Symbols();
        symbol.setKey("AAPL");
        String email = "test@example.com";

        when(symbolRepository.existsByKey(symbol.getKey())).thenReturn(false);

        symbolService.subscribe(symbol, email);

        verify(symbolRepository, times(1)).save(symbol);
        verify(userSymbolRepository, times(1)).save(any(UserSymbol.class));
    }

    @Test
    void testSubscribe_ExistingSymbol() throws UserNotFoundException {
        Symbols symbol = new Symbols();
        symbol.setKey("AAPL");
        String email = "test@example.com";

        when(symbolRepository.existsByKey(symbol.getKey())).thenReturn(true);

        symbolService.subscribe(symbol, email);

        verify(symbolRepository, never()).save(symbol);
        verify(userSymbolRepository, times(1)).save(any(UserSymbol.class));
    }


    @Test
    void testUnsubscribe_ValidSubscription() throws InvalidSubscription {
        String symbolKey = "AAPL";
        String email = "test@example.com";
        UserSymbol userSymbol = new UserSymbol();
        userSymbol.setKey(symbolKey);
        userSymbol.setMail(email);

        when(userSymbolRepository.findBySymbolAndEmail(symbolKey, email)).thenReturn(userSymbol);

        symbolService.unsubscribe(symbolKey, email);

        verify(userSymbolRepository, times(1)).deleteBySymbolAndEmail(symbolKey, email);
    }

    @Test
    void testUnsubscribe_InvalidSubscription() {
        String symbolKey = "AAPL";
        String email = "test@example.com";

        when(userSymbolRepository.findBySymbolAndEmail(symbolKey, email)).thenReturn(null);

        InvalidSubscription exception = assertThrows(InvalidSubscription.class, () ->
                symbolService.unsubscribe(symbolKey, email)
        );

        assertEquals("User subscription not found", exception.getMessage());
        verify(userSymbolRepository, never()).deleteBySymbolAndEmail(symbolKey, email);
    }
}
