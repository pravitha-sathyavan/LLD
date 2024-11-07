package model;

import service.SplitCriteria;

import java.util.List;

public class Expense {
    private String id;
    private String title;
    private String  paidBy;
    private Double amount;
    private List<Account> accounts;
    private List<Share> shares;
    private SplitCriteria splitCriteria;

    public Double getAmount() {
        return amount;
    }

    public List<Share> getShares() {
        return shares;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public Expense(String paidBy, String title, Double amount, SplitCriteria splitCriteria) {
        this.paidBy = paidBy;
        this.title = title;
        this.amount = amount;
        this.splitCriteria = splitCriteria;
    }

    public List<Share> split(){
        return splitCriteria.split(this);
    }
}


