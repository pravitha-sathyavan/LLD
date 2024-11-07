package service.Impl;

import exception.EventAlreadyaddedException;
import model.*;
import service.EventsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventsServiceImpl implements EventsService {

    private final SplitwiseContext context;

    public EventsServiceImpl(SplitwiseContext context) {
        this.context = context;
    }

    @Override
    public void createEvent(Account account, String title, String description, LocalDateTime date, List<Account> accountList) {
        Event event = new Event.Builder(title)
                .descripion(description)
                .date(date)
                .admin(account.getUsername())
                .accounts(accountList)
                .build();
        context.getSplitwise().addEvent(event);
        boolean isAdded = account.getEvents().add(event);
        if(isAdded) {
            account.setEvents(account.getEvents());
        }
    }

    @Override
    public void addMember(Event event, Account account) throws EventAlreadyaddedException {
        if(account.getEvents().contains(event)) {
            throw new EventAlreadyaddedException("Event already added");
        }
        boolean isAdded = account.getEvents().add(event);
        if(isAdded) {
            account.setEvents(account.getEvents());
        }
    }

    @Override
    public void addEvent(Event event, Expense expense) {
        List<Expense> expenses = event.getExpenses();
        expenses.add(expense);
        event.setExpenses(expenses);
    }

    @Override
    public void generateBill(Event event) {
        List<Expense> expenses = event.getExpenses();
        List<Share> shareList = new ArrayList<>();
        expenses.forEach(expense -> {
            shareList.addAll(expense.getShares());
        });
        List<Account> accountList = event.getAccounts();
        // write logic to generate bills from the above info
    }


}
