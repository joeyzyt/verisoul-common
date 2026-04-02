package ai.megaannum.api.dto;

/**
 * Represents a decrypted credential file retrieved from IPFS, containing the raw file content and its original name.
 */
public class DecryptedAssetDTO {
    private byte[] fileContent;
    private String fileName;

    public DecryptedAssetDTO() {}

    public DecryptedAssetDTO(byte[] fileContent, String fileName) {
        this.fileContent = fileContent;
        this.fileName = fileName;
    }

    public byte[] getFileContent() { return fileContent; }
    public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
