package ai.megaannum.api.service;

import java.util.Map;

public interface CertImageService {
    Map<String, Object> buildCertResponse(
            String issuerName,
            String soulName,
            String certName,
            String type);
}
