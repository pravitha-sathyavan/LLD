package model;

import exception.ContactNotFoundException;
import model.Account;
import model.Spam;
import search.TrieNodeFunctions;

import java.util.ArrayList;
import java.util.List;

public class TrueCaller {

    List<Account> users;
    Spam spamContacts;

    public TrueCaller() {
        users = new ArrayList<>();
        spamContacts = Spam.getSpamInstance();
    }

    public void addUser(Account acc) {
        users.add(acc);
        TrieNodeFunctions trieNodeFunctions = TrieNodeFunctions.getRoot();
        trieNodeFunctions.insert(acc.getUserName(),acc);
    }

    public void deleteUser(Account acc) throws ContactNotFoundException {
        if (!users.contains(acc)) {
            throw new ContactNotFoundException("Contact not found");
        }
        users.remove(acc);
    }

    public void addSpamContact(String phNo, Account acc, String reason) {
        spamContacts.addSpam(phNo, acc.getPhNo(),reason);
    }

    public void isSpam(String phNo){
        spamContacts.isSpam(phNo);
    }

    public List<Account> search(String str){
        TrieNodeFunctions trieNodeFunctions = TrieNodeFunctions.getRoot();
        return trieNodeFunctions.startsWith(str);
    }

}