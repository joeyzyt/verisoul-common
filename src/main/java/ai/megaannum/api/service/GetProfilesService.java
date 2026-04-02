package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulDTO;

import java.util.List;

/**
 * Retrieves credential profiles filtered by profiler (issuer) for a given soul.
 */
public interface GetProfilesService {

    /**
     * Returns all credential profiles issued by a specific profiler to a soul.
     *
     * @param profilerAddress the wallet address of the credential issuer
     * @param soulAddress the wallet address of the soul owner
     * @return a list of soul DTOs representing the matching profiles
     */
    List<SoulDTO> getProfilesByProfiler(String profilerAddress, String soulAddress);
}
