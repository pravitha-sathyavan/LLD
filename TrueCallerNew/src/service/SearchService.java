package search;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private final TrieNode root = new TrieNode();

    private static SearchService instance;

    private SearchService() {}

    public static SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }
        return instance;
    }

    public void addUserToTrie(String name, Account account) {
        TrieNode current = root;
        for (char c : name.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isWord = true;
        current.account = account;
    }

    public List<Account> searchByPrefix(String prefix) {
        List<Account> results = new ArrayList<>();
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                return results;
            }
            current = current.children[index];
        }
        collectAccounts(current, results);
        return results;
    }

    private void collectAccounts(TrieNode node, List<Account> results) {
        if (node == null) return;
        if (node.isWord) results.add(node.account);
        for (TrieNode child : node.children) {
            collectAccounts(child, results);
        }
    }
}


//public class TrieNodeFunctions {
//    private static TrieNode root = new TrieNode();
//    private static TrieNodeFunctions trieNodeFunctions = new TrieNodeFunctions();
//
//    public static TrieNodeFunctions getRoot() {
//        if (trieNodeFunctions == null) {
//            trieNodeFunctions = new TrieNodeFunctions();
//        }
//        return trieNodeFunctions ;
//    }
//
//    public void insert(String word, Account account) {
//        TrieNode current = root;
//        for(char c: word.toCharArray()) {
//            if(current.children[c-'a'] == null) {
//                current.children[c-'a'] = new TrieNode();
//            }
//            current = current.children[c-'a'];
//        }
//        current.isWord = true;
//        current.account = account;
//    }
//
//    public Account search(String word) {
//        TrieNode current = root;
//        for(char c: word.toCharArray()) {
//            if(current.children[c-'a'] == null) {
//                return null;
//            }
//            current = current.children[c-'a'];
//        }
//        if(current.isWord)
//            return current.account;
//        return null;
//    }
//
//    public List<Account> startsWith(String prefix) {
//        List<Account> accounts = new ArrayList<>();
//        TrieNode current = root;
//        for(char c: prefix.toCharArray()) {
//            if(current.children[c-'a'] == null) {
//                return null;
//            }
//            current = current.children[c-'a'];
//        }
//        while(current!=null){
//            for(int i=0;i<26;i++) {
//                if(current.children[i]!=null && current.children[i].isWord){
//                    accounts.add(current.children[i].account);
//                }
//            }
//        }
//        return accounts;
//    }
//}
