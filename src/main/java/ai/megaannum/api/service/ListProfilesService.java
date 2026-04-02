package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulProfileDataDTO;

/**
 * Lists all credential profiles and their issuers for a given soul owner.
 */
public interface ListProfilesService {

    /**
     * Returns all profiles and associated issuers for a soul.
     *
     * @param soulName the soul owner's name
     * @return a DTO containing the list of soul profiles and their issuers
     */
    SoulProfileDataDTO listProfiles(String soulName);
}
