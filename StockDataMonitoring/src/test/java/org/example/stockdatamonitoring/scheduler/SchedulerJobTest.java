package org.example.stockdatamonitoring.scheduler;


import org.example.stockdatamonitoring.domain.StockData;
import org.example.stockdatamonitoring.domain.StockPrice;
import org.example.stockdatamonitoring.domain.UserSymbol;
import org.example.stockdatamonitoring.domain.Users;
import org.example.stockdatamonitoring.repository.UserRepository;
import org.example.stockdatamonitoring.repository.UserSymbolRepository;
import org.example.stockdatamonitoring.service.StockDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SchedulerJobTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSymbolRepository userSymbolRepository;

    @Mock
    private StockDataService stockDataService;

    @InjectMocks
    private SchedulerJob schedulerJob;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testPrintMessage_WhenNoStockDataChanges_ShouldNotSendEmail() {
        // Arrange
        Users user = new Users("test@example.com");
        UserSymbol userSymbol = new UserSymbol("AAPL", "test@example.com");
        List<UserSymbol> userSymbols = Collections.singletonList(userSymbol);

        StockData stockData = new StockData();
        stockData.setStockPrices(Arrays.asList(
                new StockPrice(LocalDateTime.now().minusMinutes(30), "100", "101", "99", "100", "1000"),
                new StockPrice(LocalDateTime.now(), "100", "101", "99", "100", "1000")
        ));

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userSymbolRepository.findByMail(user.getEmail())).thenReturn(userSymbols);
        when(stockDataService.fetchStockData("AAPL", "1min")).thenReturn(stockData);

        // Act
        schedulerJob.printMessage();

        // Assert
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testIsStockDataChangedInPastHour_WhenDataChanged_ShouldReturnTrue() {
        // Arrange
        StockData stockData = new StockData();
        stockData.setStockPrices(Arrays.asList(
                new StockPrice(LocalDateTime.now().minusHours(1), "100", "101", "99", "100", "1000"),
                new StockPrice(LocalDateTime.now(), "100", "102", "98", "101", "1100")
        ));

        when(stockDataService.fetchStockData("AAPL", "1min")).thenReturn(stockData);

        // Act
        boolean result = schedulerJob.isStockDataChangedInPastHour("AAPL");

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsStockDataChangedInPastHour_WhenDataNotChanged_ShouldReturnFalse() {
        // Arrange
        StockData stockData = new StockData();
        stockData.setStockPrices(Arrays.asList(
                new StockPrice(LocalDateTime.now().minusMinutes(30), "100", "101", "99", "100", "1000"),
                new StockPrice(LocalDateTime.now(), "100", "101", "99", "100", "1000")
        ));

        when(stockDataService.fetchStockData("AAPL", "1min")).thenReturn(stockData);

        // Act
        boolean result = schedulerJob.isStockDataChangedInPastHour("AAPL");

        // Assert
        assertFalse(result);
    }
}
