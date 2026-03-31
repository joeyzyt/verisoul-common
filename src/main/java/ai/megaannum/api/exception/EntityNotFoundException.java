package ai.megaannum.api.exception;

public class EntityNotFoundException extends VerisoulException {
    public EntityNotFoundException(String message) { super(message); }
    public EntityNotFoundException(String message, Throwable cause) { super(message, cause); }
}
