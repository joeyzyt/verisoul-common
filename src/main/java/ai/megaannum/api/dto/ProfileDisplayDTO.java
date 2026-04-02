package ai.megaannum.api.dto;

import java.math.BigInteger;

/**
 * Represents a credential profile formatted for UI display, including issuer, file name, timestamp, and download URL.
 */
public class ProfileDisplayDTO {
    private String identity;
    private String issuer;
    private String fileName;
    private BigInteger timestamp;
    private String downloadUrl;
    private String formalType;

    public ProfileDisplayDTO() {}

    public ProfileDisplayDTO(String identity, String issuer, String fileName, BigInteger timestamp, String downloadUrl) {
        this.identity = identity;
        this.issuer = issuer;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.downloadUrl = downloadUrl;
        this.formalType = "";
    }

    public ProfileDisplayDTO(String identity, String issuer, String fileName, BigInteger timestamp, String downloadUrl, String formalType) {
        this.identity = identity;
        this.issuer = issuer;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.downloadUrl = downloadUrl;
        this.formalType = formalType;
    }

    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public BigInteger getTimestamp() { return timestamp; }
    public void setTimestamp(BigInteger timestamp) { this.timestamp = timestamp; }
    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
    public String getFormalType() { return formalType; }
    public void setFormalType(String formalType) { this.formalType = formalType; }
}
