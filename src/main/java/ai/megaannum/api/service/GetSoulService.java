package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulDTO;

public interface GetSoulService {
    SoulDTO getSoul(String userName) throws Exception;
}
