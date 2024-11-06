package model;

import exception.ContactNotFoundException;
import model.common.*;

import java.util.HashMap;
import java.util.List;

public class BusinessUser extends Account {

    private BusinessInfo  businessInfo;

    public void registerAccount(String phNo, String userName, String password, UserType userType, Tag tag, Contact contactCard, BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
        super.registerAccount(phNo, userName, password, userType, tag, contactCard);
    }

    public void upgradeUser() {
        this.setUserType(UserType.BUSINESS);
    }

}
