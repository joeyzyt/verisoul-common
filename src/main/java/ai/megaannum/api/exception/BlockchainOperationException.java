package ai.megaannum.api.exception;

/**
 * Thrown when a blockchain transaction or smart contract interaction fails.
 */
public class BlockchainOperationException extends VerisoulException {
    public BlockchainOperationException(String message) { super(message); }
    public BlockchainOperationException(String message, Throwable cause) { super(message, cause); }
}
