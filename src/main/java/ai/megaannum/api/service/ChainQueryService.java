package ai.megaannum.api.service;

import java.util.Map;

public interface ChainQueryService {
    Map<String, Object> findAndQueryTransaction(String issuerName, String soulName, String certName) throws Exception;
    Map<String, Object> queryTransactionDetails(String txHash) throws Exception;
}
