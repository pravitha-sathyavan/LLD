package service.Impl;

import model.Account;
import model.Expense;
import model.Share;
import model.SplitwiseContext;
import service.ExpenseService;
import service.SplitCriteria;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private final SplitwiseContext context;
    public ExpenseServiceImpl(SplitwiseContext context) {
        this.context = context;
    }

    @Override
    public Expense createExpense(Account account, String title, Double amount, SplitCriteria splitCriteria, List<Account> accountList) {
        Expense expense = new Expense(account.getUsername(), title, amount, splitCriteria);
        List<Share> shareList = expense.split();
        expense.setShares(shareList);
        return expense;
    }

}
