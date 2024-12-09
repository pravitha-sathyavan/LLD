package org.example.stockdatamonitoring.controller;

import org.example.stockdatamonitoring.domain.StockData;
import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.exception.InvalidSubscription;
import org.example.stockdatamonitoring.exception.InvalidSymbolException;
import org.example.stockdatamonitoring.exception.UserNotFoundException;
import org.example.stockdatamonitoring.service.StockDataService;
import org.example.stockdatamonitoring.service.SymbolService;
import org.example.stockdatamonitoring.util.ValidatorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.example.stockdatamonitoring.service.StockDataService.logger;

@RestController
@RequestMapping("/stockData")
public class StockDataController {

    private StockDataService service;
    private SymbolService symbolService;

    public StockDataController(StockDataService service, SymbolService symbolService) {
        this.service = service;
        this.symbolService = symbolService;
    }

    @GetMapping("/{symbol}/{timeInterval}")
    public StockData getStockData(@PathVariable String symbol, @PathVariable String timeInterval) throws InvalidSymbolException {
        return service.fetchStockData(symbol, timeInterval);
    }

    @GetMapping("/match/{symbol}")
    public List<Symbols> getMatchingSymbols(@PathVariable String symbol) {
        return service.fetchMatchingSymbols(symbol);
    }


    @GetMapping("/subscribedSymbols")
    public List<Symbols> getSubscribedSymbols(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return symbolService.getSubscribedSymbols(email);
    }

    @PostMapping("/subscribe/symbol")
    public void subscribeSymbol(@RequestBody Symbols symbol, @AuthenticationPrincipal OAuth2User principal) throws UserNotFoundException {
        String email = principal.getAttribute("email");
        symbolService.subscribe(symbol,email);
    }

    @PostMapping("/subscribe/symbol/{symbolname}")
    public void subscribeSymbolByEmail(@PathVariable String symbolname, @RequestBody String email) {
        Symbols symbols = new Symbols();
        symbols.setKey(symbolname);
        try {
            symbolService.subscribe(symbols,email);
        } catch (UserNotFoundException e) {
            logger.info("user email id not found");
        }
    }

    @DeleteMapping("/unsubscribe/{symbol}")
    public void unsubscribeSymbol(@PathVariable String symbol, @AuthenticationPrincipal OAuth2User principal) throws InvalidSubscription {
        String email = principal.getAttribute("email");
        symbolService.unsubscribe(symbol,email);
    }

}


