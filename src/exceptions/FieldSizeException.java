/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Exceptionklasse fuer eine falsche Feldgroesse
 */
package exceptions;

public class FieldSizeException extends RuntimeException {
    public FieldSizeException() {

    }

    public FieldSizeException(String message) {
        super(message);
    }
}
