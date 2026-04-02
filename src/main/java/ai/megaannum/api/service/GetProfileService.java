package ai.megaannum.api.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Retrieves and downloads credential profiles issued to a soul owner.
 */
public interface GetProfileService {

    /**
     * Retrieves a credential profile issued by a specific issuer to a user.
     *
     * @param issuerName the credential issuer's name
     * @param userName the soul owner's username
     * @return a response containing the profile data map
     */
    ResponseEntity<Map<String, Object>> getProfile(
            String issuerName,
            String userName
    );

    /**
     * Downloads the raw certificate file for a specific credential.
     *
     * @param issuerName the credential issuer's name
     * @param soulName the soul owner's name
     * @param identity the on-chain identity of the credential
     * @return a response containing the certificate file bytes
     */
    ResponseEntity<byte[]> downloadCertificate(
            String issuerName,
            String soulName,
            String identity
    );
}
