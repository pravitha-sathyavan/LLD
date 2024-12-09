package org.example.stockdatamonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class StockDataMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockDataMonitoringApplication.class, args);
    }
}
