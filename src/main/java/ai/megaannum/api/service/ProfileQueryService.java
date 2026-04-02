package ai.megaannum.api.service;

import ai.megaannum.api.dto.ProfileDisplayDTO;
import ai.megaannum.api.dto.DecryptedAssetDTO;

import java.util.List;

/**
 * Queries and retrieves credential profiles for display and download purposes.
 */
public interface ProfileQueryService {

    /**
     * Returns a display-ready list of all credential profiles for a soul.
     *
     * @param soulName the soul owner's name
     * @return a list of profile display DTOs
     */
    List<ProfileDisplayDTO> listProfilesDisplay(String soulName);

    /**
     * Downloads and decrypts a specific credential certificate from IPFS.
     *
     * @param issuer the credential issuer's name
     * @param soulName the soul owner's name
     * @param identity the on-chain identity of the credential
     * @return the decrypted asset containing the file content and name
     */
    DecryptedAssetDTO downloadCertificate(String issuer, String soulName, String identity);
}
