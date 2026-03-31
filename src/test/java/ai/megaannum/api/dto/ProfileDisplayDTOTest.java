package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileDisplayDTOTest {

    @Test
    void fiveArgConstructorDefaultsFormalTypeToEmpty() {
        ProfileDisplayDTO dto = new ProfileDisplayDTO(
                "cert1", "issuer1", "file.pdf", BigInteger.valueOf(1700000000), "/download/cert1"
        );

        assertThat(dto.getIdentity()).isEqualTo("cert1");
        assertThat(dto.getIssuer()).isEqualTo("issuer1");
        assertThat(dto.getFileName()).isEqualTo("file.pdf");
        assertThat(dto.getTimestamp()).isEqualTo(BigInteger.valueOf(1700000000));
        assertThat(dto.getDownloadUrl()).isEqualTo("/download/cert1");
        assertThat(dto.getFormalType()).isEmpty();
    }

    @Test
    void sixArgConstructorSetsFormalType() {
        ProfileDisplayDTO dto = new ProfileDisplayDTO(
                "cert1", "issuer1", "file.pdf", BigInteger.TEN, "/download/cert1", "Degree"
        );

        assertThat(dto.getFormalType()).isEqualTo("Degree");
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        ProfileDisplayDTO dto = new ProfileDisplayDTO();
        assertThat(dto.getIdentity()).isNull();
        assertThat(dto.getFormalType()).isNull();
    }
}
