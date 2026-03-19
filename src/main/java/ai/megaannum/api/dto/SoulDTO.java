package ai.megaannum.api.dto;

import java.math.BigInteger;

public class SoulDTO {
    private String identity;
    private String url;
    private BigInteger score;
    private BigInteger timestamp;

    public SoulDTO() {}

    public SoulDTO(String identity, String url, BigInteger score, BigInteger timestamp) {
        this.identity = identity;
        this.url = url;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public BigInteger getScore() { return score; }
    public void setScore(BigInteger score) { this.score = score; }
    public BigInteger getTimestamp() { return timestamp; }
    public void setTimestamp(BigInteger timestamp) { this.timestamp = timestamp; }
}
