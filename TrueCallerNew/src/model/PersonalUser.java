package model;

import exception.ContactNotFoundException;
import model.common.Contact;
import model.common.PersonalInfo;
import model.common.Tag;
import model.common.UserType;

import java.util.HashMap;
import java.util.List;

public class PersonalUser extends Account {
    @Override
    public void upgradeUser() {
        setUserType(UserType.PAID);
    }
}