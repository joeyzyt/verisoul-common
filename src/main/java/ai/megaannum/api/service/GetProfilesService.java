package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulDTO;

import java.util.List;

public interface GetProfilesService {
    List<SoulDTO> getProfilesByProfiler(String profilerAddress, String soulAddress) throws Exception;
}
