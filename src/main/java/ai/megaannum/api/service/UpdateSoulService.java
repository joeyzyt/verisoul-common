package ai.megaannum.api.service;

/**
 * Updates the metadata of an existing Soulbound Token on the blockchain.
 */
public interface UpdateSoulService {

    /**
     * Updates the on-chain metadata URL and description of a user's SBT.
     *
     * @param userAddress the wallet address of the soul owner
     * @param userName the soul owner's username
     * @param metadataUrl the new metadata URL (typically an IPFS link)
     * @param description the new description for the soul
     * @return a confirmation or transaction hash
     */
    String updateSBT(String userAddress, String userName, String metadataUrl, String description);
}
