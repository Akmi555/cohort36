package mail_sending.homework;

public class ConfirmationFailedException extends RuntimeException {

    public ConfirmationFailedException(String message) {
        super(message);
    }
}