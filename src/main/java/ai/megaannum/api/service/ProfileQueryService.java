package ai.megaannum.api.service;

import ai.megaannum.api.dto.ProfileDisplayDTO;
import ai.megaannum.api.dto.DecryptedAssetDTO;

import java.util.List;

public interface ProfileQueryService {

    List<ProfileDisplayDTO> listProfilesDisplay(String soulName) throws Exception;

    DecryptedAssetDTO downloadCertificate(String issuer, String soulName, String identity) throws Exception;
}
