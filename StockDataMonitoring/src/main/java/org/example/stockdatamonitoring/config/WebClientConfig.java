package org.example.stockdatamonitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //Access external apis
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
