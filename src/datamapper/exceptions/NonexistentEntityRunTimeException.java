package datamapper.exceptions;

/**
 *
 * @author 31504477
 */
public class NonexistentEntityRunTimeException extends RuntimeException {
    public NonexistentEntityRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityRunTimeException(String message) {
        super(message);
    }
}
