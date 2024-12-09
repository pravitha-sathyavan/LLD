package org.example.stockdatamonitoring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.stockdatamonitoring.domain.MetaData;
import org.example.stockdatamonitoring.domain.StockData;
import org.example.stockdatamonitoring.domain.StockPrice;
import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.util.StockDataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StockDataServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private RestTemplate restTemplate;

    private StockDataService stockDataService;

    @Value("${api.alphavantage.url}")
    private String stockDataUrl;

    @Value("${api.alphavantage.apiKey}")
    private String apiKey;

    @Value("${api.alphavantage.symbolSearchUrl}")
    private String symbolSearchUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        WebClient webClient = WebClient.builder().build();
        stockDataService = new StockDataService();
    }


    @Test
    void parseAndSaveMetaData_ShouldParseValidJsonResponse() {
        String jsonResponse = "{\"Meta Data\": {\"1. Information\": \"Mock Info\", \"2. Symbol\": \"AAPL\", \"3. Last Refreshed\": \"2024-12-04\", \"4. Interval\": \"1min\", \"5. Output Size\": \"Compact\", \"6. Time Zone\": \"US/Eastern\"}, \"Time Series (1min)\": {\"2024-12-04 10:00:00\": {\"1. open\": \"150.00\", \"2. high\": \"155.00\", \"3. low\": \"149.00\", \"4. close\": \"154.00\", \"5. volume\": \"1000\"}}}";
        String timeInterval = "1min";

        StockData stockData = stockDataService.parseAndSaveMetaData(jsonResponse, timeInterval);

        assertNotNull(stockData);
        assertNotNull(stockData.getMetaData());
        assertEquals("AAPL", stockData.getMetaData().getSymbol());
        assertEquals(1, stockData.getStockPrices().size());
    }


    @Test
    void fetchMatchingSymbols_ShouldHandleEmptyResponse() {
        String symbol = "UNKNOWN";
        String url = symbolSearchUrl + symbol + apiKey;

        String mockResponse = "{}";

        when(restTemplate.getForEntity(url, String.class)).thenReturn(ResponseEntity.ok(mockResponse));

        List<Symbols> symbols = stockDataService.fetchMatchingSymbols(symbol);

        assertNotNull(symbols);
        assertEquals(0, symbols.size());
    }
}
