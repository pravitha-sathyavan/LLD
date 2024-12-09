package org.example.stockdatamonitoring.domain;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Meta;

@Entity
@Table(name = "Usersymbol")
public class UserSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use database auto-increment
    private Long id;
    private String mail;
    private String key;

    public UserSymbol(String aapl, String mail) {
    }

    public UserSymbol() {
    }

    public String getMail() {
        return mail;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKey() {
        return key;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
