package ai.megaannum.api.service;

import ai.megaannum.api.dto.UserRecordDTO;

import java.io.File;
import java.util.List;

public interface BackendService {
    String register(String userName, String email, String password, String dateOfBirth, String gender, String major);
    String login(String userName, String password);
    String uploadFile(String formalType, String token, String userBID, File tmpFile, String certId, String userAID);
    String getFiles(String issuername, String username, String certId);
    String deleteFiles(String username, String issuerName, String certId);
    long getTotalUsersCount();
    UserRecordDTO getUserRecord(String userName);
    void removeUser(String userName);
    List<String> getAllUserNames();
}
