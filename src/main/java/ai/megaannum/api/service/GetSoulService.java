package ai.megaannum.api.service;

import ai.megaannum.api.dto.SoulDTO;

/**
 * Retrieves Soulbound Token (SBT) data for a user from the blockchain.
 */
public interface GetSoulService {

    /**
     * Returns the soul data associated with a given username.
     *
     * @param userName the username whose soul to retrieve
     * @return the soul DTO containing on-chain identity, metadata URL, score, and timestamp
     */
    SoulDTO getSoul(String userName);
}
