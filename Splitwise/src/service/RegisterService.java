package service;

import exception.AccountAlreadyExistsException;

public interface RegisterService {
    public void register(String username, String password, String mobileno, String email) throws AccountAlreadyExistsException;
    public void register(String username, String password, String mobileno) throws AccountAlreadyExistsException;
}

