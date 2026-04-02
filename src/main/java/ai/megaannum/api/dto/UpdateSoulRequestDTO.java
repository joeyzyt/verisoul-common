package ai.megaannum.api.dto;

/**
 * Request payload for updating a Soulbound Token's metadata URL and description.
 */
public class UpdateSoulRequestDTO {
    private String userName;
    private String metadataUrl;
    private String description;

    public UpdateSoulRequestDTO() {}

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getMetadataUrl() { return metadataUrl; }
    public void setMetadataUrl(String metadataUrl) { this.metadataUrl = metadataUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
