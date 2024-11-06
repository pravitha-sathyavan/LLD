import model.Spam;
import model.TrueCaller;
import model.common.UserType;
import model.Account;
import model.PersonalUser;

public class MainApplication {
    public static void main(String[] args) {
        TrueCallerApplication application = new TrueCallerApplication();
        AccountFactory accountFactory = new AccountFactory();

        Account acc1 = accountFactory.createPersonalUser("999999999", "Pravitha", "1234", UserType.FREE);
        Account acc2 = accountFactory.createPersonalUser("999999998", "Amal", "123456", UserType.FREE);

        application.addUser(acc1);
        application.addUser(acc2);

        acc1.addContact("Amal", acc2);
    }
}