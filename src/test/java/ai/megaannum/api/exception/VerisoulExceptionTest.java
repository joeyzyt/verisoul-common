package ai.megaannum.api.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VerisoulExceptionTest {

    // --- Hierarchy ---

    @Test
    void allExceptionsAreUnchecked() {
        assertThat(new VerisoulException("msg")).isInstanceOf(RuntimeException.class);
        assertThat(new EntityNotFoundException("msg")).isInstanceOf(RuntimeException.class);
        assertThat(new AuthenticationException("msg")).isInstanceOf(RuntimeException.class);
        assertThat(new BlockchainOperationException("msg")).isInstanceOf(RuntimeException.class);
        assertThat(new IpfsOperationException("msg")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void allSubclassesExtendVerisoulException() {
        assertThat(new EntityNotFoundException("msg")).isInstanceOf(VerisoulException.class);
        assertThat(new AuthenticationException("msg")).isInstanceOf(VerisoulException.class);
        assertThat(new BlockchainOperationException("msg")).isInstanceOf(VerisoulException.class);
        assertThat(new IpfsOperationException("msg")).isInstanceOf(VerisoulException.class);
    }

    // --- Message and cause propagation ---

    @Test
    void messagePreserved() {
        assertThat(new EntityNotFoundException("User not found").getMessage())
                .isEqualTo("User not found");
    }

    @Test
    void causePreserved() {
        IOException cause = new IOException("disk full");
        IpfsOperationException ex = new IpfsOperationException("Upload failed", cause);
        assertThat(ex.getCause()).isSameAs(cause);
        assertThat(ex.getMessage()).isEqualTo("Upload failed");
    }

    @Test
    void blockchainExceptionPreservesCause() {
        Exception cause = new Exception("tx reverted");
        BlockchainOperationException ex = new BlockchainOperationException("Mint failed", cause);
        assertThat(ex.getCause()).isSameAs(cause);
    }

    // --- Catchability: callers can catch specific or generic ---

    @Test
    void catchSpecificExceptionType() {
        assertThatThrownBy(() -> { throw new EntityNotFoundException("not found"); })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("not found");
    }

    @Test
    void catchBaseTypeGetsAllSubclasses() {
        assertThatThrownBy(() -> { throw new AuthenticationException("bad password"); })
                .isInstanceOf(VerisoulException.class);
    }

    // inner class for test use — java.io.IOException
    private static class IOException extends Exception {
        IOException(String msg) { super(msg); }
    }
}
