package model;

public class Share {
    private int id;
    private String account;
    private Double amount;

    public Share(String username, Double amount) {
        this.account = username;
        this.amount = amount;
    }
}
