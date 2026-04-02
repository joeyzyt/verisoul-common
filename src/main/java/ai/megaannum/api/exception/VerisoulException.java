package ai.megaannum.api.exception;

/**
 * Base runtime exception for all Verisoul platform errors.
 */
public class VerisoulException extends RuntimeException {
    public VerisoulException(String message) { super(message); }
    public VerisoulException(String message, Throwable cause) { super(message, cause); }
}
