package service;

import exception.EventAlreadyaddedException;
import model.Account;
import model.Event;
import model.Expense;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EventsService {

    public void createEvent(Account account, String title, String description, LocalDateTime date, List<Account> accountList);
   // public boolean acceptEvent(Account account, Event event);
    public void addMember(Event event, Account account) throws EventAlreadyaddedException;

    public void addEvent(Event event, Expense expense);

    void generateBill(Event event);
}
