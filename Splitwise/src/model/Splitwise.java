package model;

import exception.AccountAlreadyExistsException;
import service.EventsService;
import service.ExpenseService;
import service.RegisterService;
import service.SplitCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Splitwise {
    private List<Account> accounts;
    private Map<String, Account> accountById;
    private List<Event> events;

    private SplitwiseContext splitwiseContext;
    public Splitwise(SplitwiseContext splitwiseContext) {
        this.splitwiseContext = splitwiseContext;
        this.accounts = new ArrayList<>();
        this.accountById = new HashMap<>();
        this.events = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Map<String, Account> getAccountById() {
        return accountById;
    }

    public void setAccountById(Map<String, Account> accountById) {
        this.accountById = accountById;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }


    public void register(String username, String password, String mobileno, String email) throws AccountAlreadyExistsException {
        splitwiseContext.getRegisterService().register(username, password, mobileno, email);
    }

    public void register(String username, String password, String mobileno) throws AccountAlreadyExistsException {
        splitwiseContext.getRegisterService().register(username, password, mobileno);
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    public void addExpense(Event event, String title, Double amount, SplitCriteria criteria, Account account, List<Account> accounts) {
        Expense expense = splitwiseContext.getExpenseService().createExpense(account, title, amount, criteria,accounts);
        splitwiseContext.getEventsService().addEvent(event, expense);
        splitwiseContext.getEventsService().generateBill(event);
    }

}
