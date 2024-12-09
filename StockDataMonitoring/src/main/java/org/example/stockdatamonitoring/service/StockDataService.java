package org.example.stockdatamonitoring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.stockdatamonitoring.domain.MetaData;
import org.example.stockdatamonitoring.domain.StockData;
import org.example.stockdatamonitoring.domain.StockPrice;
import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.util.StockDataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StockDataService {

    @Value("${api.alphavantage.url}")
    private String stockDataUrl;

    @Value("${api.alphavantage.apiKey}")
    private String apiKey;

    @Value("${api.alphavantage.symbolSearchUrl}")
    private String symbolSearchUrl;

    public static final Logger logger = LoggerFactory.getLogger(StockDataService.class);
    public StockDataService() {
    }


    @Cacheable(value = "stocks", key = "#key + '-' + #timeInterval")
    public StockData fetchStockData(String key, String timeInterval) {
        String url = stockDataUrl + "&symbol=" + key + "&interval=" + timeInterval + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        logger.info("Connecting to url: " +url);
        String responseBody = response.getBody();
        StockData stockdata = parseAndSaveMetaData(responseBody,timeInterval);
        return stockdata;
    }

    public StockData parseAndSaveMetaData(String jsonResponse, String timeInterval) {
        logger.info("Parsing data retrieved from api" +jsonResponse.substring(0,100));
        StockData stockData = new StockData();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode metaDataNode = rootNode.get(StockDataConstants.META_DATA);

            MetaData metaData = new MetaData();
            if (metaDataNode != null) {
                metaData.setInformation(metaDataNode.get(StockDataConstants.INFORMATION).asText());
                metaData.setSymbol(metaDataNode.get(StockDataConstants.SYMBOL).asText());
                metaData.setLastRefreshed(metaDataNode.get(StockDataConstants.LAST_REFRESHED).asText());
                metaData.setInterval(metaDataNode.get(StockDataConstants.INTERVAL).asText());
                metaData.setOutputSize(metaDataNode.get(StockDataConstants.OUTPUT_SIZE).asText());
                metaData.setTimeZone(metaDataNode.get(StockDataConstants.TIME_ZONE).asText());
            }
            stockData.setMetaData(metaData);
            JsonNode timeSeriesNode = rootNode.get(StockDataConstants.TIME_SERIES_PREFIX + timeInterval + ")");
            List<StockPrice> stockPrices = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StockDataConstants.DATE_TIME_FORMAT);

            if (timeSeriesNode != null) {
                Iterator<String> fieldNames = timeSeriesNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String dateTime = fieldNames.next();
                    JsonNode priceData = timeSeriesNode.get(dateTime);
                    StockPrice stockPrice = new StockPrice();
                    stockPrice.setDateTime(LocalDateTime.parse(dateTime, formatter));
                    stockPrice.setOpen(priceData.get(StockDataConstants.OPEN).asDouble());
                    stockPrice.setHigh(priceData.get(StockDataConstants.HIGH).asDouble());
                    stockPrice.setLow(priceData.get(StockDataConstants.LOW).asDouble());
                    stockPrice.setClose(priceData.get(StockDataConstants.CLOSE).asDouble());
                    stockPrice.setVolume(priceData.get(StockDataConstants.VOLUME).asDouble());
                    stockPrices.add(stockPrice);
                }
            }
            stockData.setStockPrices(stockPrices);
        } catch (Exception e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        return stockData;
    }

    public List<Symbols> fetchMatchingSymbols(String symbol) {
        String apiUrl = symbolSearchUrl + symbol + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = response.getBody();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode bestMatchesNode = rootNode.path(StockDataConstants.BEST_MATCHES);

            List<Symbols> stockNames = new ArrayList<>();

            if (bestMatchesNode.isArray()) {
                for (JsonNode match : bestMatchesNode) {
                    String stockName = match.path(StockDataConstants.STOCK_NAME).asText();
                    String stockSymbol = match.path(StockDataConstants.STOCK_SYMBOL).asText();
                    Symbols symbolObj = new Symbols();
                    symbolObj.setName(stockName);
                    symbolObj.setKey(stockSymbol);
                    stockNames.add(symbolObj);
                }
            }
            return stockNames;
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
