package exception;

import model.Account;

public class AccountAlreadyExistsException extends Exception {

    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }
}
