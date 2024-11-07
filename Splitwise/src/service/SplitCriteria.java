package service;

import model.Expense;
import model.Share;

import java.util.List;

public interface SplitCriteria {
    public List<Share> split(Expense expense);
}
