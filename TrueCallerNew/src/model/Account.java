public abstract class Account {
    private String phNo;
    private String userName;
    private String password;
    private UserType userType;
    private Map<String, Account> contactList = new HashMap<>();
    private List<String> blockedContacts = new ArrayList<>();

    public void registerAccount(String phNo, String userName, String password, UserType userType) {
        this.phNo = phNo;
        this.password = password;
        this.userName = userName;
        this.userType = userType;
    }

    public void addContact(String contactName, Account user) {
        contactList.put(contactName, user);
        SearchService.getInstance().addUserToTrie(contactName, user);
    }

    public void blockContact(String phNo) {
        blockedContacts.add(phNo);
    }

    public void unBlockContact(String phNo) {
        blockedContacts.remove(phNo);
    }

    public abstract void upgradeUser();
}
