package ai.megaannum.api.service;

public interface WalletService {
    String getWalletAddress(String userName);
    String getUserNameByAddress(String address);
}
