package ie.baloot5.exception;

public class NotEnoughAmountException extends RuntimeException {

    public NotEnoughAmountException(String message) {
        super(message);
    }

    public NotEnoughAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
