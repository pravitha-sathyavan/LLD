package org.example.stockdatamonitoring.scheduler;
import org.example.stockdatamonitoring.domain.StockData;
import org.example.stockdatamonitoring.domain.StockPrice;
import org.example.stockdatamonitoring.domain.UserSymbol;
import org.example.stockdatamonitoring.domain.Users;
import org.example.stockdatamonitoring.repository.UserRepository;
import org.example.stockdatamonitoring.repository.UserSymbolRepository;
import org.example.stockdatamonitoring.service.StockDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.example.stockdatamonitoring.service.StockDataService.logger;
import static org.example.stockdatamonitoring.util.StockDataConstants.*;

@Component
public class SchedulerJob {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final UserSymbolRepository userSymbolRepository;
    private final StockDataService stockDataService;
    private final ConcurrentLinkedQueue<String> symbolsToProcess = new ConcurrentLinkedQueue<>();


    @Value("${api.alphavantage.url}")
    private String stockDataUrl;

    @Value("${api.alphavantage.apiKey}")
    private String apiKey;


    public SchedulerJob(JavaMailSender mailSender, UserRepository userRepository, UserSymbolRepository userSymbolRepository, StockDataService stockDataService) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.userSymbolRepository = userSymbolRepository;
        this.stockDataService = stockDataService;
    }

    // Scheduled to run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void printMessage() {
        symbolsToProcess.clear();
        if (mailSender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) mailSender;

            Properties properties = javaMailSenderImpl.getJavaMailProperties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.auth", "true");

            List<Users> users = userRepository.findAll();
            users.forEach(user -> {
                List<UserSymbol> userSymbols = userSymbolRepository.findByMail(user.getEmail());
                List<String> symbolsChanged = new ArrayList<>();
                userSymbols.forEach(userSymbol -> {
                    if(isStockDataChangedInPastHour(userSymbol.getKey())){
                        symbolsChanged.add(userSymbol.getKey());
                    }
                });
                if(!symbolsChanged.isEmpty()) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(SENDER);
                    message.setTo(user.getEmail());
                    message.setSubject(SUBJECT);
                    String changedSymbols = String.join("\n", symbolsChanged);
                    message.setText(MAILTEXT + changedSymbols);
                    try {
                        mailSender.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public boolean isStockDataChangedInPastHour(String key) {
        if(symbolsToProcess.contains(key)){
            return true;
        }else {
            logger.info("Checking stock data changes for symbol:" + key);
            StockData stockData = stockDataService.fetchStockData(key, "1min");
            List<StockPrice> stockPrices = stockData.getStockPrices();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (stockData != null && stockData.getMetaData() != null && stockData.getMetaData().getLastRefreshed()!=null) {
                LocalDateTime lastRefreshed = LocalDateTime.parse(stockData.getMetaData().getLastRefreshed(), formatter);
                logger.info("Last refreshed date:");
                logger.info(lastRefreshed.toString());
                boolean isChanged = checkForChangesInPastHour(stockPrices, lastRefreshed);
                if (isChanged) {
                    symbolsToProcess.offer(key);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkForChangesInPastHour(List<StockPrice> stockPrices, LocalDateTime lastRefreshed) {
       // LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        LocalDateTime oneHourAgo = lastRefreshed.minusHours(1);
        List<StockPrice> recentStockPrices = stockPrices.stream()
                .filter(stockPrice -> stockPrice.getDateTime().isAfter(oneHourAgo))
                .collect(Collectors.toList());

        if (recentStockPrices.size() == 0) {
            return false;
        }
        StockPrice firstPrice = recentStockPrices.get(0);
        StockPrice lastPrice = recentStockPrices.get(recentStockPrices.size() - 1);
        return !firstPrice.getOpen().equals(lastPrice.getOpen()) ||
                !firstPrice.getHigh().equals(lastPrice.getHigh()) ||
                !firstPrice.getLow().equals(lastPrice.getLow()) ||
                !firstPrice.getClose().equals(lastPrice.getClose()) ||
                !firstPrice.getVolume().equals(lastPrice.getVolume());
    }

}


