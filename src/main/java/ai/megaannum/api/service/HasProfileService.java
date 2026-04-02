package ai.megaannum.api.service;

/**
 * Checks whether a credential profile exists for a given issuer-soul pair.
 */
public interface HasProfileService {

    /**
     * Determines if the specified profiler has issued a credential to the given user.
     *
     * @param profilerName the credential issuer's name
     * @param userName the soul owner's username
     * @return true if a profile exists, false otherwise
     */
    boolean hasProfile(String profilerName, String userName);
}
