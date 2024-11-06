import model.Spam;
import model.TrueCaller;
import model.common.UserType;
import model.Account;
import model.PersonalUser;

public class MainApplication {
    public static void main(String[] args) {
        TrueCaller application = new TrueCaller();
        Account acc1 = new PersonalUser();
        acc1.registerAccount("999999999","Pravitha","1234", UserType.FREE);

        Account acc2 = new PersonalUser();
        acc2.registerAccount("999999998","Amal","123456", UserType.FREE);

        Account acc3 = new PersonalUser();
        acc3.registerAccount("999999997","Sidharth","123554", UserType.FREE);

        Account acc4 = new PersonalUser();
        acc4.registerAccount("999999996","Aditi","123477", UserType.FREE);

        application.addUser(acc1);
        application.addUser(acc2);
        application.addUser(acc3);
        application.addUser(acc4);

        acc1.addContact("Amalll",acc2);
        acc1.addContact("Sidhu",acc3);
        acc1.addContact("Ammu",acc4);

    }
}
