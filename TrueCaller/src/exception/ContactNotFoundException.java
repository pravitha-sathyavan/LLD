package exception;

public class ContactNotFoundException extends Exception {
    public ContactNotFoundException(String msg){
        super(msg);
    }

    public ContactNotFoundException(String msg, Exception exception){
        super(msg, exception);
    }
}
