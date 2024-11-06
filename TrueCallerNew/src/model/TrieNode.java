package search;

import model.Account;

public class TrieNode {
    Account account;
    TrieNode[] children = new TrieNode[26];
    boolean isWord = false;
}

