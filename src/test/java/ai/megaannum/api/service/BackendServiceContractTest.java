package ai.megaannum.api.service;

import ai.megaannum.api.dto.UserRecordDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Abstract contract test for {@link BackendService}.
 * Subclass in the implementation module. Implementations that depend on
 * blockchain/IPFS should mock those dependencies before calling {@link #createService()}.
 */
public abstract class BackendServiceContractTest {

    protected abstract BackendService createService();

    private BackendService svc;

    @BeforeEach
    void setUp() {
        svc = createService();
    }

    // --- register ---

    @Test
    void registerReturnsNonNullResult() throws Exception {
        String result = svc.register("alice", "alice@test.com", "pass123", "1995-01-01", "female", "CS");
        assertThat(result).isNotNull();
    }

    @Test
    void registerStoresRetrievableUserRecord() throws Exception {
        svc.register("bob", "bob@test.com", "pass123", "1990-06-15", "male", "EE");

        UserRecordDTO record = svc.getUserRecord("bob");
        assertThat(record).isNotNull();
        assertThat(record.getEmail()).isEqualTo("bob@test.com");
    }

    @Test
    void registerIncreasesTotalCount() throws Exception {
        long before = svc.getTotalUsersCount();
        svc.register("carol", "carol@test.com", "pass", "2000-01-01", "female", "Math");
        assertThat(svc.getTotalUsersCount()).isGreaterThan(before);
    }

    // --- login ---

    @Test
    void loginWithCorrectPasswordReturnsToken() throws Exception {
        svc.register("dave", "dave@test.com", "secret", "1985-03-20", "male", "Phys");
        String token = svc.login("dave", "secret");
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    void loginWithWrongPasswordFails() throws Exception {
        svc.register("eve", "eve@test.com", "correct", "1992-07-10", "female", "Bio");
        assertThatThrownBy(() -> svc.login("eve", "wrong"))
                .isInstanceOf(Exception.class);
    }

    @Test
    void loginWithNonexistentUserFails() {
        assertThatThrownBy(() -> svc.login("ghost", "pass"))
                .isInstanceOf(Exception.class);
    }

    // --- user management ---

    @Test
    void removeUserMakesItUnretrievable() throws Exception {
        svc.register("frank", "frank@test.com", "pass", "1988-12-01", "male", "Art");
        assertThat(svc.getUserRecord("frank")).isNotNull();

        svc.removeUser("frank");
        assertThat(svc.getUserRecord("frank")).isNull();
    }

    @Test
    void getAllUserNamesIncludesRegisteredUsers() throws Exception {
        svc.register("grace", "grace@test.com", "pass", "1997-04-22", "female", "Law");
        List<String> names = svc.getAllUserNames();
        assertThat(names).contains("grace");
    }
}
