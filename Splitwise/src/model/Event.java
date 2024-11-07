package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event {
    private String id;
    private String title;
    private String descripion;
    private LocalDateTime date;
    private String  admin;
    private List<Account> accounts;
    private List<Bill> bills;
    private List<Expense> expenses;
    private Status status;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Event(Builder builder) {
        this.id = builder.title;
        this.title = builder.title;
        this.descripion = builder.descripion;
        this.date = builder.date;
        this.admin = builder.admin;
        this.accounts = builder.accounts;
        this.bills = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.status = Status.CREATED;
    }


    public static class Builder{
        private String id;
        private String title;
        private String descripion;
        private LocalDateTime date;
        private String admin;
        private List<Account> accounts;
        private List<Bill> bills;
        private List<Expense> expenses;
        private Status status;

        public Builder(String title){
            this.title = title;
        }
        public Builder descripion(String descripion){
            this.descripion = descripion;
            return this;
        }
        public Builder date(LocalDateTime date){
            this.date = date;
            return this;
        }
        public Builder admin(String admin){
            this.admin = admin;
            return this;
        }
        public Builder accounts(List<Account> accounts){
            this.accounts = accounts;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }

}
