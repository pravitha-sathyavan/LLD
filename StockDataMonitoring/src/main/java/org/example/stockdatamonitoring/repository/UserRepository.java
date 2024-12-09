package org.example.stockdatamonitoring.repository;


import org.example.stockdatamonitoring.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
}