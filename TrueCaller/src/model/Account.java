package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.ContactNotFoundException;
import model.common.*;
import search.TrieNodeFunctions;

public abstract class Account {

    private String id;
    private String phNo;
    private String userName;
    private String password;
    private LocalDateTime lastAccess;

    private Tag tag; //associate an account with a tag (useful with business)

    private Contact contactCard; // To store own contact details

    private UserType userType;
    private Map<String, Account> contactList;
    private List<String> blockedContacts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }


    public Contact getContactCard() {
        return contactCard;
    }

    public void setContactCard(Contact contactCard) {
        this.contactCard = contactCard;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Map<String, Account> getContactList() {
        return contactList;
    }

    public void setContactList(Map<String, Account> contactList) {
        this.contactList = contactList;
    }

    public List<String> getBlockedContacts() {
        return blockedContacts;
    }

    public void setBlockedContacts(List<String> blockedContacts) {
        this.blockedContacts = blockedContacts;
    }

    public void registerAccount(String phNo, String userName, String password, UserType userType){
        this.setPhNo(phNo);
        this.setPassword(password);
        this.setUserName(userName);
        this.setUserType(userType);
    }

    public void registerAccount(String phNo, String userName, String password, UserType userType, Tag tag, Contact contactCard){
        this.setPhNo(phNo);
        this.setPassword(password);
        this.setUserName(userName);
        this.setUserType(userType);
        this.setTag(tag);
        this.setContactCard(contactCard);
    }

    public  void addContact(String contactName, Account user){
        this.contactList.put(contactName, user);
        TrieNodeFunctions trieNodeFunctions = TrieNodeFunctions.getRoot();
        trieNodeFunctions.insert(contactName, user);
    }

    public void removeContact(String contactName) throws ContactNotFoundException {
            if(!this.contactList.containsKey(contactName))
                throw new ContactNotFoundException("Contact not found");
            this.contactList.remove(contactName);
    }

    public  void blockContact(String phNo){
        this.blockedContacts.add(phNo);
    }
    public void unBlockContact(String phNo){
        this.blockedContacts.remove(phNo);
    }

    public void reportSpam(String phNo){
        Spam spam = Spam.getSpamInstance();
        spam.addSpam(phNo, this.getPhNo(),"Unnecessary");
    }

    public abstract void upgradeUser();

    public  boolean isBlocked(String phNo){
        return blockedContacts.contains(phNo);
    }
}
