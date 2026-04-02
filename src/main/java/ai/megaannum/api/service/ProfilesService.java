package ai.megaannum.api.service;

import ai.megaannum.api.dto.ProfilesUtils;

import java.util.List;

/**
 * Manages CRUD operations for credential profile records in the local profiles database.
 */
public interface ProfilesService {

    /**
     * Adds a new credential profile record.
     *
     * @param profile the profile record to add
     */
    void add(ProfilesUtils profile);

    /**
     * Deletes a credential profile record by user address and certificate ID.
     *
     * @param userAddress the wallet address of the credential owner
     * @param certId the certificate identifier
     */
    void delete(String userAddress, String certId);

    /**
     * Updates an existing credential profile record.
     *
     * @param userAddress the wallet address of the credential owner
     * @param certId the certificate identifier
     * @param updated the updated profile data
     */
    void update(String userAddress, String certId, ProfilesUtils updated);

    /**
     * Finds a single credential profile by username and certificate ID.
     *
     * @param userName the username of the credential owner
     * @param certId the certificate identifier
     * @return the matching profile record, or null if not found
     */
    ProfilesUtils findOne(String userName, String certId);

    /**
     * Finds all credential profiles matching a given credential type.
     *
     * @param formalType the credential type to filter by
     * @return a list of matching profile records
     */
    List<ProfilesUtils> findAll(String formalType);

    /**
     * Checks whether a credential profile exists for a given user and certificate.
     *
     * @param userAddress the wallet address of the credential owner
     * @param certId the certificate identifier
     * @return true if the profile exists, false otherwise
     */
    boolean find(String userAddress, String certId);
}
