/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Exceptionklasse fuer eine nicht verfuegbare Farbe.
 */
package exceptions;

public class ColorNotAvailableException extends RuntimeException {
    public ColorNotAvailableException() {
    }

    public ColorNotAvailableException(String message) {
        super(message);
    }
}
