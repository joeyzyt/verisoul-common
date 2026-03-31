package ai.megaannum.api.service;

import ai.megaannum.api.dto.ProfilesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract contract test for {@link ProfilesService}.
 * Subclass in the implementation module and provide a real instance via {@link #createService()}.
 */
public abstract class ProfilesServiceContractTest {

    protected abstract ProfilesService createService();

    private ProfilesService svc;

    @BeforeEach
    void setUp() {
        svc = createService();
    }

    // --- add + find ---

    @Test
    void addThenFindReturnsTrue() {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xabc");
        svc.add(p);
        assertThat(svc.find("Alice", "C001")).isTrue();
    }

    @Test
    void findReturnsFalseForNonexistent() {
        assertThat(svc.find("nobody", "C999")).isFalse();
    }

    @Test
    void findReturnsFalseForNullInputs() {
        assertThat(svc.find(null, "C001")).isFalse();
        assertThat(svc.find("Alice", null)).isFalse();
    }

    // --- add + findOne ---

    @Test
    void addThenFindOneReturnsSameData() {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xabc");
        svc.add(p);

        ProfilesUtils found = svc.findOne("Alice", "C001");
        assertThat(found).isNotNull();
        assertThat(found.FormalType).isEqualTo("Degree");
        assertThat(found.From).isEqualTo("MIT");
        assertThat(found.TxHash).isEqualTo("0xabc");
    }

    @Test
    void findOneReturnsNullForNonexistent() {
        ProfilesUtils found = svc.findOne("nobody", "C999");
        assertThat(found).isNull();
    }

    @Test
    void findIsCaseInsensitiveOnUserAddress() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x"));
        assertThat(svc.find("alice", "C001")).isTrue();
        assertThat(svc.find("ALICE", "C001")).isTrue();
    }

    // --- delete ---

    @Test
    void deleteRemovesProfile() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x"));
        assertThat(svc.find("Alice", "C001")).isTrue();

        svc.delete("Alice", "C001");
        assertThat(svc.find("Alice", "C001")).isFalse();
    }

    @Test
    void deleteNonexistentDoesNotThrow() {
        svc.delete("nobody", "C999"); // should not throw
    }

    // --- update ---

    @Test
    void updateReplacesMatchingProfile() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xold"));

        ProfilesUtils updated = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xnew");
        svc.update("Alice", "C001", updated);

        ProfilesUtils found = svc.findOne("Alice", "C001");
        assertThat(found).isNotNull();
        assertThat(found.TxHash).isEqualTo("0xnew");
    }

    // --- findAll ---

    @Test
    void findAllWithNullTypeReturnsEverything() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x"));
        svc.add(new ProfilesUtils("Certificate", "Harvard", "Bob", "C002", "0x"));

        List<ProfilesUtils> all = svc.findAll(null);
        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void findAllFiltersbyFormalType() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x"));
        svc.add(new ProfilesUtils("Certificate", "Harvard", "Bob", "C002", "0x"));

        List<ProfilesUtils> degrees = svc.findAll("Degree");
        assertThat(degrees).allMatch(p -> p.FormalType.equalsIgnoreCase("Degree"));
    }

    @Test
    void findAllWithEmptyTypeReturnsEverything() {
        svc.add(new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x"));
        List<ProfilesUtils> all = svc.findAll("");
        assertThat(all).isNotEmpty();
    }
}
