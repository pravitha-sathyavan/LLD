package org.example.stockdatamonitoring.service;

import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.domain.UserSymbol;
import org.example.stockdatamonitoring.exception.InvalidSubscription;
import org.example.stockdatamonitoring.exception.UserNotFoundException;
import org.example.stockdatamonitoring.repository.SymbolRepository;
import org.example.stockdatamonitoring.repository.UserRepository;
import org.example.stockdatamonitoring.repository.UserSymbolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.stockdatamonitoring.service.StockDataService.logger;

@Service
public class SymbolService {

    private final SymbolRepository symbolRepository;
    private final UserSymbolRepository userSymbolRepository;
    private final UserRepository userRepository;

    public SymbolService(SymbolRepository symbolRepository, UserSymbolRepository userSymbolRepository, UserRepository userRepository) {
        this.symbolRepository = symbolRepository;
        this.userSymbolRepository = userSymbolRepository;
        this.userRepository = userRepository;
    }

    public void subscribe(Symbols symbol, String email) throws UserNotFoundException {
        if(!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("Email Id not found");
        }
        if(!symbolRepository.existsByKey(symbol.getKey()))
            symbolRepository.save(symbol);
        UserSymbol userSymbol = new UserSymbol();
        userSymbol.setKey(symbol.getKey());
        userSymbol.setMail(email);
        userSymbolRepository.save(userSymbol);
    }

    public List<Symbols> getSubscribedSymbols(String email) {
        return userSymbolRepository.findSubscribedSymbolsByEmail(email);
    }

    public void unsubscribe(String symbol, String email) throws InvalidSubscription {
        UserSymbol userSymbol = userSymbolRepository.findBySymbolAndEmail(symbol, email);
        if (userSymbol != null) {
            userSymbolRepository.deleteBySymbolAndEmail(symbol,email);
        } else{
            throw new InvalidSubscription("User subscription not found");
        }
    }
}
