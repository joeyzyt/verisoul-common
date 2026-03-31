package ai.megaannum.api.service;

import java.util.Map;

public interface ChainQuery2Service {
    Map<String, Object> getCoreChainStats();
    Map<String, Object> getTxPoolFluctuation();
}
