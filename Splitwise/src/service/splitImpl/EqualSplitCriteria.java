package service.splitImpl;

import model.Account;
import model.Expense;
import model.Share;
import service.SplitCriteria;

import java.util.ArrayList;
import java.util.List;

public class EqualSplitCriteria implements SplitCriteria {
    @Override
    public List<Share> split(Expense expense) {
        List<Share> shareList = new ArrayList<>();
        List<Account> accountList = expense.getAccounts();
        Double amount = expense.getAmount()/accountList.size();
        accountList.forEach(account -> {
            shareList.add(new Share(account.getUsername(), amount));
        });
        return shareList;
    }
}
