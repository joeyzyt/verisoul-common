package ai.megaannum.api.service;

import ai.megaannum.api.dto.ProfilesUtils;

import java.util.List;

public interface ProfilesService {
    void add(ProfilesUtils profile);
    void delete(String userAddress, String certId);
    void update(String userAddress, String certId, ProfilesUtils updated);
    ProfilesUtils findOne(String userName, String certId);
    List<ProfilesUtils> findAll(String formalType);
    boolean find(String userAddress, String certId);
}
