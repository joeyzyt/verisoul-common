package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulProfileDataDTO;

public interface ListProfilesService {
    SoulProfileDataDTO listProfiles(String soulName);
}
