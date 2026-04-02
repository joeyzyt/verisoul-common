package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateSoulRequestDTOTest {

    @Test
    void settersAndGettersRoundTrip() {
        UpdateSoulRequestDTO dto = new UpdateSoulRequestDTO();
        dto.setUserName("alice");
        dto.setMetadataUrl("ipfs://QmABC");
        dto.setDescription("Updated profile");

        assertThat(dto.getUserName()).isEqualTo("alice");
        assertThat(dto.getMetadataUrl()).isEqualTo("ipfs://QmABC");
        assertThat(dto.getDescription()).isEqualTo("Updated profile");
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        UpdateSoulRequestDTO dto = new UpdateSoulRequestDTO();
        assertThat(dto.getUserName()).isNull();
        assertThat(dto.getMetadataUrl()).isNull();
        assertThat(dto.getDescription()).isNull();
    }
}
