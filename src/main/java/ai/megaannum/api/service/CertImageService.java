package ai.megaannum.api.service;

import java.util.Map;

/**
 * Builds display-ready certificate image responses for credential visualization.
 */
public interface CertImageService {

    /**
     * Constructs a response map containing certificate image data and metadata.
     *
     * @param issuerName the name of the credential issuer
     * @param soulName the name of the soul (credential owner)
     * @param certName the certificate identifier
     * @param type the credential type
     * @return a map containing the certificate image and associated metadata
     */
    Map<String, Object> buildCertResponse(
            String issuerName,
            String soulName,
            String certName,
            String type);
}
