package ai.megaannum.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract contract test for {@link WalletService}.
 * Subclass in the implementation module and provide a real instance via {@link #createService()}.
 * Also implement {@link #registerWallet(String, String)} to seed test data.
 */
public abstract class WalletServiceContractTest {

    protected abstract WalletService createService();

    /** Seed a username→address mapping for testing. */
    protected abstract void registerWallet(String userName, String address);

    private WalletService svc;

    @BeforeEach
    void setUp() {
        svc = createService();
    }

    @Test
    void getWalletAddressReturnsNullForUnknownUser() {
        assertThat(svc.getWalletAddress("nonexistent")).isNull();
    }

    @Test
    void getWalletAddressReturnsAddressAfterRegistration() {
        registerWallet("alice", "0xAliceAddress");
        assertThat(svc.getWalletAddress("alice")).isEqualTo("0xAliceAddress");
    }

    @Test
    void getUserNameByAddressReturnsNullForUnknownAddress() {
        assertThat(svc.getUserNameByAddress("0xUnknown")).isNull();
    }

    @Test
    void getUserNameByAddressReturnsNameAfterRegistration() {
        registerWallet("bob", "0xBobAddress");
        assertThat(svc.getUserNameByAddress("0xBobAddress")).isEqualTo("bob");
    }

    @Test
    void bidirectionalLookupIsConsistent() {
        registerWallet("carol", "0xCarolAddr");
        String addr = svc.getWalletAddress("carol");
        String name = svc.getUserNameByAddress(addr);
        assertThat(name).isEqualTo("carol");
    }
}
