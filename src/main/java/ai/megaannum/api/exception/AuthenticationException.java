package ai.megaannum.api.exception;

public class AuthenticationException extends VerisoulException {
    public AuthenticationException(String message) { super(message); }
    public AuthenticationException(String message, Throwable cause) { super(message, cause); }
}
