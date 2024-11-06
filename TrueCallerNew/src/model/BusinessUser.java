public class BusinessUser extends Account {
    private BusinessInfo businessInfo;

    public void registerAccount(String phNo, String userName, String password, UserType userType, BusinessInfo businessInfo) {
        super.registerAccount(phNo, userName, password, userType);
        this.businessInfo = businessInfo;
    }

    @Override
    public void upgradeUser() {
        setUserType(UserType.BUSINESS);
    }
}
