package ai.megaannum.api.exception;

/**
 * Thrown when a requested entity such as a user, soul, or credential profile cannot be found.
 */
public class EntityNotFoundException extends VerisoulException {
    public EntityNotFoundException(String message) { super(message); }
    public EntityNotFoundException(String message, Throwable cause) { super(message, cause); }
}
