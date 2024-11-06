package search;

import model.Account;

public class TrieNode {
    Account account;
    TrieNode[] children;
    boolean isWord;

    TrieNode(){
        account = null;
        children = new TrieNode[26];
        isWord = false;
    }
}
