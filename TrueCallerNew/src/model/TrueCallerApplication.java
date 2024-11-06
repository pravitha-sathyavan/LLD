package model;

import exception.ContactNotFoundException;
import model.Account;
import model.Spam;
import search.TrieNodeFunctions;

import java.util.ArrayList;
import java.util.List;

/*
Coupling with TrieNodeFunctions:
Directly accessing TrieNodeFunctions from the Account class increases coupling.
Correction: Use a SearchService interface and inject it, enhancing flexibility and testability.
 */

public class TrueCallerApplication {
    private final List<Account> users = new ArrayList<>();
    private final SpamService spamService = SpamService.getInstance();
    private final SearchService searchService = SearchService.getInstance();

    public void addUser(Account account) {
        users.add(account);
        searchService.addUserToTrie(account.getUserName(), account);
    }

    public void deleteUser(Account account) {
        users.remove(account);
    }

    public void reportSpam(String spamNumber, String reportingNumber, String reason) {
        spamService.reportSpam(spamNumber, reportingNumber, reason);
    }

    public boolean isSpam(String phNo) {
        return spamService.isSpam(phNo);
    }

    public List<Account> searchUsers(String prefix) {
        return searchService.searchByPrefix(prefix);
    }
}


//
//public class TrueCaller {
//
//    List<Account> users;
//    Spam spamContacts;
//
//    public TrueCaller() {
//        users = new ArrayList<>();
//        spamContacts = Spam.getSpamInstance();
//    }
//
//    public void addUser(Account acc) {
//        users.add(acc);
//        TrieNodeFunctions trieNodeFunctions = TrieNodeFunctions.getRoot();
//        trieNodeFunctions.insert(acc.getUserName(),acc);
//    }
//
//    public void deleteUser(Account acc) throws ContactNotFoundException {
//        if (!users.contains(acc)) {
//            throw new ContactNotFoundException("Contact not found");
//        }
//        users.remove(acc);
//    }
//
//    public void addSpamContact(String phNo, Account acc, String reason) {
//        spamContacts.addSpam(phNo, acc.getPhNo(),reason);
//    }
//
//    public void isSpam(String phNo){
//        spamContacts.isSpam(phNo);
//    }
//
//    public List<Account> search(String str){
//        TrieNodeFunctions trieNodeFunctions = TrieNodeFunctions.getRoot();
//        return trieNodeFunctions.startsWith(str);
//    }
//
//}