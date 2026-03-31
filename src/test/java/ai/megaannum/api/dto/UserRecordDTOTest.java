package ai.megaannum.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRecordDTOTest {

    @Test
    void fullConstructorSetsAllFields() {
        UserRecordDTO dto = new UserRecordDTO("alice@mit.edu", "1995-01-15", "female", "CS", "PhD student");

        assertThat(dto.getEmail()).isEqualTo("alice@mit.edu");
        assertThat(dto.getDateOfBirth()).isEqualTo("1995-01-15");
        assertThat(dto.getGender()).isEqualTo("female");
        assertThat(dto.getMajor()).isEqualTo("CS");
        assertThat(dto.getDescription()).isEqualTo("PhD student");
    }

    @Test
    void noArgConstructorFieldsAreNull() {
        UserRecordDTO dto = new UserRecordDTO();
        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getDateOfBirth()).isNull();
    }
}
