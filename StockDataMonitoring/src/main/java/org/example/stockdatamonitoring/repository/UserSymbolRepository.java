package org.example.stockdatamonitoring.repository;

import org.example.stockdatamonitoring.domain.Symbols;
import org.example.stockdatamonitoring.domain.UserSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserSymbolRepository extends JpaRepository<UserSymbol, Long> {

    List<UserSymbol> findByMail(String mail);

    @Query("SELECT s FROM Symbols s JOIN UserSymbol us ON s.key = us.key WHERE us.mail = :email")
    List<Symbols> findSubscribedSymbolsByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserSymbol u WHERE u.key = :symbol AND u.mail = :email")
    UserSymbol findBySymbolAndEmail(String symbol, String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserSymbol u WHERE u.key = :symbol AND u.mail = :email")
    void deleteBySymbolAndEmail(@Param("symbol") String symbol, @Param("email") String email);



}
