package be.looorent.anpr;

/**
 * @author Lorent Lempereur
 */
public class WiegandException extends RuntimeException {
    WiegandException(String message, Throwable cause) {
        super(message, cause);
    }
}
