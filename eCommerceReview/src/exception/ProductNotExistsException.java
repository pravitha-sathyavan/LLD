package exception;

public class ProductNotExistsException extends Exception {
    public ProductNotExistsException(String message) {
        super(message);
    }
}
