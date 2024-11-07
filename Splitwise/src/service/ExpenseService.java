package service;

import model.Account;
import model.Expense;

import java.util.List;

public interface ExpenseService {

    public Expense createExpense(Account account, String title, Double amount, SplitCriteria splitCriteria, List<Account> accountList);

}
