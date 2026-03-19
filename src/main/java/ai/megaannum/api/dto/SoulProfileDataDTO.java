package ai.megaannum.api.dto;

import java.util.List;

public class SoulProfileDataDTO {
    private List<SoulDTO> souls;
    private List<String> issuers;

    public SoulProfileDataDTO() {}

    public SoulProfileDataDTO(List<SoulDTO> souls, List<String> issuers) {
        this.souls = souls;
        this.issuers = issuers;
    }

    public List<SoulDTO> getSouls() { return souls; }
    public void setSouls(List<SoulDTO> souls) { this.souls = souls; }
    public List<String> getIssuers() { return issuers; }
    public void setIssuers(List<String> issuers) { this.issuers = issuers; }
}
