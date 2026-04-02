package ai.megaannum.api.service;

import java.util.Map;

/**
 * Provides advanced blockchain analytics including chain statistics and transaction pool monitoring.
 */
public interface ChainQuery2Service {

    /**
     * Retrieves core blockchain statistics such as block height, peer count, and gas metrics.
     *
     * @return a map of chain statistic names to their values
     */
    Map<String, Object> getCoreChainStats();

    /**
     * Retrieves transaction pool fluctuation data for monitoring network congestion.
     *
     * @return a map of transaction pool metrics
     */
    Map<String, Object> getTxPoolFluctuation();
}
