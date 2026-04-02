package ai.megaannum.api.service;

/**
 * Resolves mappings between usernames and Ethereum wallet addresses.
 */
public interface WalletService {

    /**
     * Returns the Ethereum wallet address associated with a username.
     *
     * @param userName the username to look up
     * @return the wallet address
     */
    String getWalletAddress(String userName);

    /**
     * Returns the username associated with an Ethereum wallet address.
     *
     * @param address the wallet address to look up
     * @return the username
     */
    String getUserNameByAddress(String address);
}
