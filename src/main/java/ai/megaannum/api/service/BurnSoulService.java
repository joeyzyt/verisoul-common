package ai.megaannum.api.service;

/**
 * Handles the permanent destruction (burning) of a user's Soulbound Token on-chain.
 */
public interface BurnSoulService {

    /**
     * Burns the Soulbound Token associated with the authenticated user.
     *
     * @param token the authentication token identifying the soul owner
     * @return a confirmation or transaction hash
     */
    String burnSBT(String token);
}
