package ai.megaannum.api.exception;

public class BlockchainOperationException extends VerisoulException {
    public BlockchainOperationException(String message) { super(message); }
    public BlockchainOperationException(String message, Throwable cause) { super(message, cause); }
}
