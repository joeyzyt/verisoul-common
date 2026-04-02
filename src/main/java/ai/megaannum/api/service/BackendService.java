package ai.megaannum.api.service;

import ai.megaannum.api.dto.UserRecordDTO;

import java.io.File;
import java.util.List;

/**
 * Provides core backend operations for user registration, authentication, and credential file management.
 */
public interface BackendService {

    /**
     * Registers a new user account in the system.
     *
     * @param userName the unique username for the new account
     * @param email the user's email address
     * @param password the user's password
     * @param dateOfBirth the user's date of birth
     * @param gender the user's gender
     * @param major the user's major or field of study
     * @return a confirmation or token string upon successful registration
     */
    String register(String userName, String email, String password, String dateOfBirth, String gender, String major);

    /**
     * Authenticates a user and returns a session token.
     *
     * @param userName the username to authenticate
     * @param password the user's password
     * @return a session token upon successful login
     */
    String login(String userName, String password);

    /**
     * Uploads an encrypted credential file to IPFS and records it on-chain.
     *
     * @param formalType the credential type (e.g. diploma, license)
     * @param token the authentication token of the issuer
     * @param userBID the wallet address of the credential recipient
     * @param tmpFile the file to upload
     * @param certId the unique certificate identifier
     * @param userAID the wallet address of the issuer
     * @return the IPFS hash or transaction result
     */
    String uploadFile(String formalType, String token, String userBID, File tmpFile, String certId, String userAID);

    /**
     * Retrieves credential files issued by a specific issuer to a specific user.
     *
     * @param issuername the issuer's username
     * @param username the recipient's username
     * @param certId the certificate identifier
     * @return the file data or download reference
     */
    String getFiles(String issuername, String username, String certId);

    /**
     * Deletes credential files for a given user and issuer.
     *
     * @param username the recipient's username
     * @param issuerName the issuer's username
     * @param certId the certificate identifier
     * @return a confirmation string
     */
    String deleteFiles(String username, String issuerName, String certId);

    /**
     * Returns the total number of registered users.
     *
     * @return the total user count
     */
    long getTotalUsersCount();

    /**
     * Retrieves the profile record for a given user.
     *
     * @param userName the username to look up
     * @return the user's profile record
     */
    UserRecordDTO getUserRecord(String userName);

    /**
     * Removes a user account from the system.
     *
     * @param userName the username to remove
     */
    void removeUser(String userName);

    /**
     * Returns a list of all registered usernames.
     *
     * @return all usernames in the system
     */
    List<String> getAllUserNames();
}
