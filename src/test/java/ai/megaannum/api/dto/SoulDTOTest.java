package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class SoulDTOTest {

    @Test
    void fullConstructorSetsAllFields() {
        SoulDTO soul = new SoulDTO("alice", "ipfs://Qm123", BigInteger.valueOf(85), BigInteger.valueOf(1700000000));

        assertThat(soul.getIdentity()).isEqualTo("alice");
        assertThat(soul.getUrl()).isEqualTo("ipfs://Qm123");
        assertThat(soul.getScore()).isEqualTo(BigInteger.valueOf(85));
        assertThat(soul.getTimestamp()).isEqualTo(BigInteger.valueOf(1700000000));
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        SoulDTO soul = new SoulDTO();

        assertThat(soul.getIdentity()).isNull();
        assertThat(soul.getUrl()).isNull();
        assertThat(soul.getScore()).isNull();
        assertThat(soul.getTimestamp()).isNull();
    }

    @Test
    void settersOverwriteFields() {
        SoulDTO soul = new SoulDTO("alice", "url1", BigInteger.ONE, BigInteger.TEN);
        soul.setIdentity("bob");
        soul.setUrl("url2");
        soul.setScore(BigInteger.valueOf(99));
        soul.setTimestamp(BigInteger.ZERO);

        assertThat(soul.getIdentity()).isEqualTo("bob");
        assertThat(soul.getUrl()).isEqualTo("url2");
        assertThat(soul.getScore()).isEqualTo(BigInteger.valueOf(99));
        assertThat(soul.getTimestamp()).isEqualTo(BigInteger.ZERO);
    }
}
