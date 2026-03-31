package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoulProfileDataDTOTest {

    @Test
    void constructorSetsPairedLists() {
        List<SoulDTO> souls = List.of(
                new SoulDTO("alice", "url1", BigInteger.ONE, BigInteger.TEN),
                new SoulDTO("bob", "url2", BigInteger.TWO, BigInteger.TEN)
        );
        List<String> issuers = List.of("0xIssuer1", "0xIssuer2");

        SoulProfileDataDTO dto = new SoulProfileDataDTO(souls, issuers);

        assertThat(dto.getSouls()).hasSize(2);
        assertThat(dto.getIssuers()).hasSize(2);
        assertThat(dto.getSouls().get(0).getIdentity()).isEqualTo("alice");
        assertThat(dto.getIssuers().get(0)).isEqualTo("0xIssuer1");
    }

    @Test
    void parallelListsSameLengthInvariant() {
        List<SoulDTO> souls = List.of(
                new SoulDTO("a", "u", BigInteger.ZERO, BigInteger.ZERO),
                new SoulDTO("b", "u", BigInteger.ZERO, BigInteger.ZERO),
                new SoulDTO("c", "u", BigInteger.ZERO, BigInteger.ZERO)
        );
        List<String> issuers = List.of("0x1", "0x2", "0x3");

        SoulProfileDataDTO dto = new SoulProfileDataDTO(souls, issuers);
        assertThat(dto.getSouls()).hasSameSizeAs(dto.getIssuers());
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        SoulProfileDataDTO dto = new SoulProfileDataDTO();
        assertThat(dto.getSouls()).isNull();
        assertThat(dto.getIssuers()).isNull();
    }

    @Test
    void emptyListsAreValid() {
        SoulProfileDataDTO dto = new SoulProfileDataDTO(List.of(), List.of());
        assertThat(dto.getSouls()).isEmpty();
        assertThat(dto.getIssuers()).isEmpty();
    }
}
