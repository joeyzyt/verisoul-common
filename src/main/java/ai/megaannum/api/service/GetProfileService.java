package ai.megaannum.api.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GetProfileService {

    ResponseEntity<Map<String, Object>> getProfile(
            String issuerName,
            String userName
    );

    ResponseEntity<byte[]> downloadCertificate(
            String issuerName,
            String soulName,
            String identity
    );
}
