package org.example.stockdatamonitoring.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Symbols")
public class Symbols {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use database auto-increment
    private Long id;
    private String name;
    private String key;

    public Symbols(String aapl) {
    }

    public Symbols() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}


