package ai.megaannum.api.exception;

/**
 * Thrown when user authentication fails due to invalid credentials or an expired token.
 */
public class AuthenticationException extends VerisoulException {
    public AuthenticationException(String message) { super(message); }
    public AuthenticationException(String message, Throwable cause) { super(message, cause); }
}
