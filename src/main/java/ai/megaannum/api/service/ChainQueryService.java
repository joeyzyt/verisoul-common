package ai.megaannum.api.service;

import java.util.Map;

/**
 * Queries the blockchain for credential-related transaction data.
 */
public interface ChainQueryService {

    /**
     * Finds and returns the transaction associated with a specific credential issuance.
     *
     * @param issuerName the credential issuer's name
     * @param soulName the soul owner's name
     * @param certName the certificate identifier
     * @return a map of transaction details
     */
    Map<String, Object> findAndQueryTransaction(String issuerName, String soulName, String certName);

    /**
     * Queries detailed information for a specific transaction by its hash.
     *
     * @param txHash the transaction hash to look up
     * @return a map of transaction details
     */
    Map<String, Object> queryTransactionDetails(String txHash);
}
