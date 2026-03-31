package ai.megaannum.api.service;

import ai.megaannum.api.dto.DecryptedAssetDTO;
import ai.megaannum.api.dto.SoulDTO;

public interface SbtDecryptionService {
    DecryptedAssetDTO getAndDecryptProfile(String issuerAddress, String userName, String certId);
    String getFileNameOnly(SoulDTO soul);
}
