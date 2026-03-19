package ai.megaannum.api.service;

import java.util.Map;

public interface ChainQuery2Service {
    Map<String, Object> getCoreChainStats() throws Exception;
    Map<String, Object> getTxPoolFluctuation() throws Exception;
}
