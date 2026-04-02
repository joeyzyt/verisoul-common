package ai.megaannum.api.exception;

/**
 * Thrown when an IPFS operation such as file upload, download, or pinning fails.
 */
public class IpfsOperationException extends VerisoulException {
    public IpfsOperationException(String message) { super(message); }
    public IpfsOperationException(String message, Throwable cause) { super(message, cause); }
}
