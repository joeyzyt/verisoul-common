package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecryptedAssetDTOTest {

    @Test
    void fullConstructorSetsFields() {
        byte[] content = {0x01, 0x02, 0x03};
        DecryptedAssetDTO dto = new DecryptedAssetDTO(content, "diploma.pdf");

        assertThat(dto.getFileContent()).isEqualTo(content);
        assertThat(dto.getFileName()).isEqualTo("diploma.pdf");
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        DecryptedAssetDTO dto = new DecryptedAssetDTO();
        assertThat(dto.getFileContent()).isNull();
        assertThat(dto.getFileName()).isNull();
    }

    @Test
    void handlesEmptyContent() {
        DecryptedAssetDTO dto = new DecryptedAssetDTO(new byte[0], "empty.txt");
        assertThat(dto.getFileContent()).isEmpty();
        assertThat(dto.getFileName()).isEqualTo("empty.txt");
    }
}
