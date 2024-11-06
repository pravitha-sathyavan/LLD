package model;

import exception.ContactNotFoundException;
import model.common.Contact;
import model.common.PersonalInfo;
import model.common.Tag;
import model.common.UserType;

import java.util.HashMap;
import java.util.List;

public class PersonalUser extends Account {

    private PersonalInfo personalInfo;

    public void registerAccount(String phNo, String userName, String password, UserType userType, Tag tag, Contact contactCard, PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        super.registerAccount(phNo, userName, password, userType, tag, contactCard);
    }

    public void upgradeUser() {
        this.setUserType(UserType.PAID);
    }

}
