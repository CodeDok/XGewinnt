/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Exceptionklasse, wenn kein Match gefunden wurde
 */
package exceptions;

public class NoMatchException extends RuntimeException {

    public NoMatchException() {
    }

    public NoMatchException(String message) {
        super(message);
    }
}
