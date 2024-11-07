package model;

public class Bill {
    private Account fromAccount;
    private Account toAccount;
    private Double amount;
    private Status status;

    public Bill(Account fromAccount, Account toAccount, Double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = Status.NOTPAID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
