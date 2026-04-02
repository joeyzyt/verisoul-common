package ai.megaannum.api.service;

/**
 * Checks whether a user has a minted Soulbound Token on the blockchain.
 */
public interface HasSoulService {

    /**
     * Determines if the specified user owns a Soulbound Token.
     *
     * @param userName the username to check
     * @return true if the user has a soul, false otherwise
     */
    boolean hasSoul(String userName);
}
