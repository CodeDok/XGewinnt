/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Exceptionklasse fuer eine nicht verfuegbare Methode
 */
package pack;

public class UnavailableMethodException extends RuntimeException{

    public UnavailableMethodException(String message) {
        super(message);
    }
}
