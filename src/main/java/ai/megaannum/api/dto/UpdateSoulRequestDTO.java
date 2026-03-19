package ai.megaannum.api.dto;

public class UpdateSoulRequestDTO {
    private String useName;
    private String metadataUrl;
    private String description;

    public UpdateSoulRequestDTO() {}

    public String getUseName() { return useName; }
    public void setUseName(String useName) { this.useName = useName; }
    public String getMetadataUrl() { return metadataUrl; }
    public void setMetadataUrl(String metadataUrl) { this.metadataUrl = metadataUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
