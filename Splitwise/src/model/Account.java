package model;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private String username;
    private String id;
    private String password;
    private String mobileno;
    private String email;
    List<Event> events;

    public Account(String username, String password, String mobileno) {
        this.username = username;
        this.password = password;
        this.mobileno = mobileno;
        this.events = new ArrayList<Event>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Account(String username, String password, String mobileno, String email) {
        this.username = username;
        this.password = password;
        this.mobileno = mobileno;
        this.email = email;
        this.events = new ArrayList<>();
    }

    public String getUsername() {
         return username;
    }
}
