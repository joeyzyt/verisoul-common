package ai.megaannum.api.service;

import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    void recordLogin(String token);
    void recordLogout(String token);
    long getLoggedInUsersCount();
}
