package ai.megaannum.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a credential profile record in the local profiles database, mapping to JSON entries in ProfilesDB.
 */
public class ProfilesUtils {

    // Maps to JSON: "FormalType" : "Graduation Certificate"
    @JsonProperty("FormalType")
    public String FormalType;

    // Maps to JSON: "From" : "Beijing University of Posts and Telecommunications"
    @JsonProperty("From")
    public String From;

    // Maps to JSON: "To" : "Li Xiaohua"
    @JsonProperty("To")
    public String To;

    // Maps to JSON: "CertId" : "Li Xiaohua Bachelor's Graduation Certificate"
    @JsonProperty("CertId")
    public String CertId;

    // Maps to JSON: "Hx" : "0xe6..."
    @JsonProperty("Hx")
    public String TxHash;

    // No-arg constructor (required for Jackson deserialization)
    public ProfilesUtils() {}

    // All-args constructor
    public ProfilesUtils(String formalType, String from, String to, String certId, String txHash) {
        this.FormalType = formalType;
        this.From = from;
        this.To = to;
        this.CertId = certId;
        this.TxHash = txHash;
    }

    // For convenient debug printing
    @Override
    public String toString() {
        return "ProfilesUtils{" +
                "FormalType='" + FormalType + '\'' +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", CertId='" + CertId + '\'' +
                ", Hx='" + TxHash + '\'' +
                '}';
    }

    // Compatibility methods, since some places in the Service layer may use getOwner-style accessors.
    // If you only access the public fields directly, these getters are optional.
    @JsonIgnore
    public String getOwner() { return To; }
    @JsonIgnore
    public String getCertName() { return CertId; }
}
