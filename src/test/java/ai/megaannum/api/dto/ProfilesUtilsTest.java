package ai.megaannum.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfilesUtilsTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void jacksonDeserializesHxFieldToTxHash() throws JsonProcessingException {
        String json = """
                {"FormalType":"Degree","From":"MIT","To":"Alice","CertId":"C001","Hx":"0xabc123"}
                """;
        ProfilesUtils p = mapper.readValue(json, ProfilesUtils.class);

        assertThat(p.TxHash).isEqualTo("0xabc123");
        assertThat(p.FormalType).isEqualTo("Degree");
        assertThat(p.From).isEqualTo("MIT");
        assertThat(p.To).isEqualTo("Alice");
        assertThat(p.CertId).isEqualTo("C001");
    }

    @Test
    void jacksonSerializesWithCorrectJsonKeys() throws JsonProcessingException {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xabc123");
        String json = mapper.writeValueAsString(p);

        assertThat(json).contains("\"Hx\":");
        assertThat(json).contains("\"FormalType\":");
        assertThat(json).contains("\"From\":");
        assertThat(json).contains("\"To\":");
        assertThat(json).contains("\"CertId\":");
        assertThat(json).doesNotContain("\"TxHash\":");
    }

    @Test
    void jacksonRoundTrip() throws JsonProcessingException {
        ProfilesUtils original = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xabc");
        String json = mapper.writeValueAsString(original);
        ProfilesUtils restored = mapper.readValue(json, ProfilesUtils.class);

        assertThat(restored.FormalType).isEqualTo(original.FormalType);
        assertThat(restored.From).isEqualTo(original.From);
        assertThat(restored.To).isEqualTo(original.To);
        assertThat(restored.CertId).isEqualTo(original.CertId);
        assertThat(restored.TxHash).isEqualTo(original.TxHash);
    }

    @Test
    void getOwnerReturnsTosField() {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x");
        assertThat(p.getOwner()).isEqualTo("Alice");
    }

    @Test
    void getCertNameReturnsCertIdField() {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0x");
        assertThat(p.getCertName()).isEqualTo("C001");
    }

    @Test
    void toStringContainsAllFields() {
        ProfilesUtils p = new ProfilesUtils("Degree", "MIT", "Alice", "C001", "0xabc");
        String str = p.toString();

        assertThat(str).contains("Degree");
        assertThat(str).contains("MIT");
        assertThat(str).contains("Alice");
        assertThat(str).contains("C001");
        assertThat(str).contains("0xabc");
    }

    @Test
    void noArgConstructorCreatesEmptyInstance() {
        ProfilesUtils p = new ProfilesUtils();
        assertThat(p.FormalType).isNull();
        assertThat(p.From).isNull();
        assertThat(p.To).isNull();
        assertThat(p.CertId).isNull();
        assertThat(p.TxHash).isNull();
    }

    @Test
    void jacksonDeserializesPartialJson() throws JsonProcessingException {
        String json = """
                {"FormalType":"Degree","To":"Alice"}
                """;
        ProfilesUtils p = mapper.readValue(json, ProfilesUtils.class);

        assertThat(p.FormalType).isEqualTo("Degree");
        assertThat(p.To).isEqualTo("Alice");
        assertThat(p.From).isNull();
        assertThat(p.CertId).isNull();
        assertThat(p.TxHash).isNull();
    }
}
