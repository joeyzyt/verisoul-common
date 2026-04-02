package ai.megaannum.api.service;

import ai.megaannum.api.dto.DecryptedAssetDTO;
import ai.megaannum.api.dto.SoulDTO;

/**
 * Handles decryption of credential assets stored on IPFS for Soulbound Tokens.
 */
public interface SbtDecryptionService {

    /**
     * Retrieves a credential from IPFS and decrypts it using the issuer's keys.
     *
     * @param issuerAddress the wallet address of the credential issuer
     * @param userName the soul owner's username
     * @param certId the certificate identifier
     * @return the decrypted asset containing the file content and name
     */
    DecryptedAssetDTO getAndDecryptProfile(String issuerAddress, String userName, String certId);

    /**
     * Extracts the original file name from a soul's metadata URL.
     *
     * @param soul the soul DTO containing the metadata URL
     * @return the extracted file name
     */
    String getFileNameOnly(SoulDTO soul);
}
