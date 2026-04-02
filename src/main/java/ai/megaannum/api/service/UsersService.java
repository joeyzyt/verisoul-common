package ai.megaannum.api.service;

/**
 * Tracks user session activity including login and logout events.
 */
public interface UsersService {

    /**
     * Records a user login event.
     *
     * @param token the authentication token of the logged-in user
     */
    void recordLogin(String token);

    /**
     * Records a user logout event.
     *
     * @param token the authentication token of the logged-out user
     */
    void recordLogout(String token);

    /**
     * Returns the count of currently logged-in users.
     *
     * @return the number of active user sessions
     */
    long getLoggedInUsersCount();
}
