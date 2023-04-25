package ie.baloot5.exception;

public class NotEnoughAmountException extends Exception {

    public NotEnoughAmountException(String message) {
        super(message);
    }

    public NotEnoughAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
