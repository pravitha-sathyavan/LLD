package service.Impl;

import exception.AccountAlreadyExistsException;
import model.Account;
import model.Splitwise;
import model.SplitwiseContext;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {
    private final SplitwiseContext context;


    public RegisterServiceImpl(SplitwiseContext context) {
        this.context = context;
    }

    public void register(String username, String password, String mobileno, String email) throws AccountAlreadyExistsException {

        if(context.getSplitwise().getAccountById().containsKey(username)){
            throw new AccountAlreadyExistsException(username + " already exists");
        }
        Account account = new Account(username, password, mobileno, email);
        context.getSplitwise().getAccounts().add(account);
        context.getSplitwise().getAccountById().put(username, account);
    }

    public void register(String username, String password, String mobileno) throws AccountAlreadyExistsException {
        if(context.getSplitwise().getAccountById().containsKey(username)){
            throw new AccountAlreadyExistsException(username + " already exists");
        }
        Account account = new Account(username, password, mobileno);
        context.getSplitwise().getAccounts().add(account);
        context.getSplitwise().getAccountById().put(username, account);
    }

}
