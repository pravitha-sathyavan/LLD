package org.example.stockdatamonitoring.repository;

import org.example.stockdatamonitoring.domain.Symbols;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymbolRepository extends JpaRepository<Symbols, Long> {
    boolean existsByKey(String key);

    Symbols getSymbolsByKey(String key);
}
