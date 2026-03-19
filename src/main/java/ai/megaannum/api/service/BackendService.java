package ai.megaannum.api.service;

import ai.megaannum.api.dto.UserRecordDTO;

import java.io.File;
import java.util.List;

public interface BackendService {
    String register(String userName, String email, String password, String dateOfBirth, String gender, String major) throws Exception;
    String login(String userName, String password) throws Exception;
    String uploadFile(String formalType, String token, String userBID, File tmpFile, String certId, String userAID) throws Exception;
    String getFiles(String issuername, String username, String certId) throws Exception;
    String deleteFiles(String username, String issuerName, String certId) throws Exception;
    long getTotalUsersCount();
    UserRecordDTO getUserRecord(String userName);
    void removeUser(String userName);
    List<String> getAllUserNames();
}
