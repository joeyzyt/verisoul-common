package ai.megaannum.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract contract test for {@link UsersService}.
 * Subclass in the implementation module and provide a real instance via {@link #createService()}.
 */
public abstract class UsersServiceContractTest {

    protected abstract UsersService createService();

    private UsersService svc;

    @BeforeEach
    void setUp() {
        svc = createService();
    }

    @Test
    void initialCountIsZero() {
        assertThat(svc.getLoggedInUsersCount()).isZero();
    }

    @Test
    void loginIncreasesCount() {
        svc.recordLogin("alice");
        assertThat(svc.getLoggedInUsersCount()).isEqualTo(1);
    }

    @Test
    void logoutDecreasesCount() {
        svc.recordLogin("alice");
        svc.recordLogout("alice");
        assertThat(svc.getLoggedInUsersCount()).isZero();
    }

    @Test
    void duplicateLoginDoesNotDoubleCount() {
        svc.recordLogin("alice");
        svc.recordLogin("alice");
        assertThat(svc.getLoggedInUsersCount()).isEqualTo(1);
    }

    @Test
    void logoutWithoutLoginDoesNotGoNegative() {
        svc.recordLogout("ghost");
        assertThat(svc.getLoggedInUsersCount()).isZero();
    }

    @Test
    void multipleUsersTrackedIndependently() {
        svc.recordLogin("alice");
        svc.recordLogin("bob");
        assertThat(svc.getLoggedInUsersCount()).isEqualTo(2);

        svc.recordLogout("alice");
        assertThat(svc.getLoggedInUsersCount()).isEqualTo(1);
    }
}
